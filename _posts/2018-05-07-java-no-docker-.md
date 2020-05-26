---
title: Java no docker - O que você precisa saber para não FALHAR
toc: true
---

This post was originally posted on [Red Hat Developers](https://developers.redhat.com/blog/2017/03/14/java-inside-docker/) and this version is a translation to Portuguease


Olá a todos. Há um ano atrás eu publiquei este post no site [Red Hat Developers](https://developers.redhat.com/blog/2017/03/14/java-inside-docker/). Desde então muitas coisas mudaram. o JDK9 trouxe algumas melhorias, e o JDK10 agora é totalmente preparado para ser executado dentro de um container.

Este post é uma tradução do post original para Portugues, com o intuito de ajudar a comunidade de língua brasileira a compreender melhor as nuances da JVM dentro de um container. Então vamos lá:

Muitos desenvolvedores estão (ou deveriam estar) a par que processos Java executados dentro deu um container linunux([docker](https://github.com/moby/moby), [rkt](https://coreos.com/rkt/), [runC](https://github.com/opencontainers/runc/), [lxcfs](https://linuxcontainers.org/lxcfs/manpages/man1/lxcfs.1.html), etc) não se comportam como esperado quando nós deixamos o "ergonomics" da JVM ajustar os valores padrão para o "garbage collector", tamanho da "heap", e o compilador "runtime".
Quando executamos uma aplicação Java sem nenhuma parâmetro de tunning como por exemplo *“java -jar mypplication-fat.jar”*, a JVM irá ajustar sozinha vários parâmetros para obter a melhor performance no ambiente de execução.

Este post mostra uma abordagem direta para mostrar aos desenvolvedores, o que eles precisam saber quando estiverem empacotando suas aplicações Java dentro de containers Linux.

Temos a tendência de pensar que containers são como máquinas virtuais onde podemos completamente definir um número de CPUs e memórias virtuais. Containers, na verdade, são mais parecidos com um mecanismo de isolamento onde recursos (CPU, memória, sistema de arquivos, rede, etc.) para um processo, são isolados de outro. Este processo de isolamento é possível através de uma funcionalidade do Kernel chamado [cgroups](https://en.wikipedia.org/wiki/Cgroups).

Entretanto, algumas aplicações que coletam informação do ambiente de execução foram implementadas antes da existência do cgroups. Ferramentas como *‘top‘, ‘free‘, ‘ps‘*, e até mesmo a JVM, não são otimizadas para serem executadas dentro de um container, especialmente quando os recursos são limitados. Vamos ver como isto funciona:

## O problema

Para propósitos de demonstração, eu criei um "docker daemon" em uma máquina virtual com 1GB de RAM usando o comando *“docker-machine create -d virtualbox –virtualbox-memory ‘1024’ docker1024”*.  Em seguida, eu executei o comando "free -h" em três distribuições Linux executando dentro de um container com 100MB de RAM e Swap como limite. O resultado é que todas elas mostram 995MB como memória total

{: .center}
![](/assets/images/docker1024.png)

Até mesmo em um cluster Kubernetes/OpenShift, o resultado é semelhante. Eu execute um Pod Kubernetes com o limite de memória de 512MB (usando o comando run mycentos –image=centos -it –limits=’memory=512Mi'”*) em um cluster com 15GB de RAM, e o total de memória apresentado foi 14GB.

{: .center}
![](/assets/images/kubernetes-java.png)

Para entender porquê isto acontece, eu sugiro que leia o post [“Memory inside Linux containers – Or why don’t free and top work in a Linux container?”](https://fabiokung.com/2014/03/13/memory-inside-linux-containers/)  do meu coterrâneo brasileiro [Fabio Kung](https://www.linkedin.com/in/fabiokung/).

Precisamos entender que os switches do docker  (-m, –memory and –memory-swap) e do kubernetes (–limits) instruem ao Kernel do Linux a __matar o processo__ se ele tenta exceder o limite de memória especificada, mas a JVM é completamente alheia destes limites. E quando excedemos estes limites, coisas ruins acontecem!

Para simular o processo sendo morto após excedermos o limite de memória especificado, vamos executar o WildFly Application Server em um container com 50MB de limite de memória através do comando *“docker run -it –name mywildfly -m=50m jboss/wildfly”*. Durante a execução do container, podemos executar o comando "docker stats" para verificar o limite do container.

{: .center}
![](/assets/images/docker-stats.png)

Mas após alguns segundo, o container executando o WildFly será interrompido e informará a mensagem: *** JBossAS process (55) received KILL signal ***

O comando *“docker inspect mywildfly -f ‘{{json .State}}'”* mostra que este container foi morto por causa de uma situação OOM (Out of Memory). Perceba o OOMKilled=true no “state” do container.

{: .center}
![](/assets/images/docker-OOMKilled.png)

## Como isto afeta aplicações Java?

Em um "docker daemon" executando em uma máquina com 1GB de RAM (previamente criada com *“docker-machine create -d virtualbox  –virtualbox-memory ‘1024’ docker1024”*) mas tendo o limite de memória restringido em 150 MB, o que é suficiente para executar a seguinte aplicacão [Spring Boot](https://github.com/redhat-developer-demos/java-container), uma aplicação Java foi inicializada com o parâmetro -XX:+PrintFlagsFinal definidos no  [Dockerfile](https://github.com/redhat-developer-demos/java-container/blob/master/Dockerfile.openjdk#L5). Este parâmetro permite ler as configurações da ergonomics da JVM.

Vamos tentar:

  `$ docker run -it --rm --name mycontainer150 -p 8080:8080 -m 150M rafabene/java-container:openjdk`
  
Eu preparei um endpoint REST em “/api/memory/” que carrega a memória da JVM com objetos String para simular uma operação que consume basntate memória. Vamos invoca-lo uma vez:

~~~~
  $ curl http://`docker-machine ip docker1024`:8080/api/memory
~~~~
  
Este endpoint irá responder com uma mensagem semelhante a **“Allocated more than 80% (219.8 MiB) of the max allowed JVM memory size (241.7 MiB)”**

Aqui podemos extrair pelo menos duas perguntas:

- Por que a memória máxima da JVM é 241.7 MiB?
- Se este container restring a memória a 150MB, por quê foi permitido alocar quase 220MB?

Primeiro, precisamos lembrar o que [a página da JVM](https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gc-ergonomics.html) diz sobre  **“maximum heap size”**. É dito que será 1/4 of the physical memory. Uma vez que a JVM não saber que está executando dentro de um container, ela irá permitir uma Heap de quase 260MB. Dado que adicionamos a flag -XX:+PrintFlagsFinal durante a inicialização do container, nós podemos checar este valore:

~~~~
  $ docker logs mycontainer150|grep -i MaxHeapSize
    uintx MaxHeapSize := 262144000 {product}
~~~~

Segundo, precisamos entender que o parâmetros "-m 150M" no comando do docker irá limitar 150MB na RAM e 150MB de Swap. Como resultado, o processo poderá alocar 300MB, e isto explicada o por quê o processo não recebeu nenhum "kill" do Kernel.

Mais combinações entre o limite de memória (-memory) e swap (-memory-swap) no comando do docker, podem ser encontrados [aqui](https://docs.docker.com/engine/reference/run/#user-memory-constraints).

{: .center}
![](/assets/images/java-docker.png)

## Mais memória é a solução?

Desenvolvedores que não entedem o probema, tendem a pensa que o ambiente não fornece memória suficiente para executar a JVM. Uma solução frequente é provisionar um ambiente com mais memória, mas isto **fará com que o problema se agrave ainda mais**.

Vamos supor que mudemos nosso "daemon" de 1GB para 8GB (criado com *“docker-machine create -d virtualbox –virtualbox-memory ‘8192’ docker8192”*), e nosso container de 150M para 600M:

  `$ docker run -it --name mycontainer -p 8080:8080 -m 600M rafabene/java-container:openjdk`

Note que o comando "curl http://docker-machine ip docker8192:8080/api/memory" nem mesmo completa a execução desta vez por quê o valor calculado de MaxHeapSize para a JVM em um ambiente de 8GB é 2092957696 bytes (~ 2GB). Check with "docker logs mycontainer" by yourself.


![](/assets/images/docker-logs-mycontainer.png)


A aplição irá tentar alocar mais de 1.6GB de memória, o que é mais que o limite deste container (600MB in RAM + 600MB in Swap) e o **processo será morto**.

Fica evidente que aumentar a memória e deixar a JVM ajustar os próprios parâmetros, nem sempre é uma boa idéia quando executados dentro de containers. Quando executamos aplicações Java dentro de containers, devemos manualmente ajusar o tamanho máximo da Heap (-Xmx parameter) baseado na real necessidade da aplicação e no limite do container.

## Qual a solução?


Uma pequena mudanção no [Dockerfile](https://github.com/redhat-developer-demos/java-container/blob/master/Dockerfile.openjdk-env#L5) permite que o usuário especifique uma variável de ambiente que define parâmtros extra para a JVM. Veja a seguinte linha:

  `CMD java -XX:+PrintFlagsFinal $JAVA_OPTIONS -jar java-container.jar`
  
Agora podemos usar a variável de ambiente JAVA_OPTIONS para informar o tamanho da Heap da JVM. 300MB parece ser o suficiente para esta aplicação. Posteriormente cheque os logs e veja o valor de  [314572800 bytes ( 300MBi)](https://www.google.com.br/search?q=314572800+bytes+to+Mebibyte&gws_rd=cr&ei=Qs5fWfWhPInGwAT4qrL4Dw)

Para o docker, você pode especificar a variável de ambiente usando o switch *“-e”*.

~~~~
$ docker run -d --name mycontainer8g -p 8080:8080 -m 600M -e JAVA_OPTIONS='-Xmx300m' rafabene/java-container:openjdk-env

$ docker logs mycontainer8g|grep -i MaxHeapSize
uintx    MaxHeapSize := 314572800       {product}
~~~~

No Kubernetes, você pode ajustar o valor da variável de ambeiente usando o switch “–env=[chave=valor]”:

~~~~
$ kubectl run mycontainer --image=rafabene/java-container:openjdk-env --limits='memory=600Mi' --env="JAVA_OPTIONS='-Xmx300m'"

$ kubectl get pods
NAME                          READY  STATUS    RESTARTS AGE
mycontainer-2141389741-b1u0o  1/1    Running   0        6s

$ kubectl logs mycontainer-2141389741-b1u0o|grep MaxHeapSize
uintx     MaxHeapSize := 314572800     {product}
~~~~


## Dá para ficar ainda melhor?

E se o valor da Heap pudesse ser calculado automaticamente baseado nas restrições do container?

Isto é possível se você usar uma imagem docker [fabric8/java-jboss-openjdk8-jdk](https://hub.docker.com/r/fabric8/java-jboss-openjdk8-jdk/) fornecida pela comunidade do [Fabric8](https://fabric8.io/). A imagem utiliza um  [script](https://github.com/fabric8io-images/java/blob/master/images/jboss/openjdk8/jdk/run-java.sh#L162-L175) que calcula as restrições do container e usa 50% da memória disponível como limite superior da Heap. Note que esta razão de 50% pode ser sobreescrito. Você também pode usar esta imagem para habilitar/desabilitar debug, diagnósticos e muito mais. Veja como um [Dockerfile](https://github.com/redhat-developer-demos/java-container/blob/master/Dockerfile.fabric8) para estar aplicação Spring Boot se parece:

~~~~
FROM fabric8/java-jboss-openjdk8-jdk:1.4.0

ENV JAVA_APP_JAR java-container.jar
ENV AB_OFF true

EXPOSE 8080

ADD target/$JAVA_APP_JAR /deployments/
~~~~

Pronto! Agora, não importa qual o limite de memória do container, nossa aplicação Java irá sempre ajustar o tamanho da heap de acordo com o container e não de acordo com o daemon.

{: .center}
![](/assets/images/docker-nolimit.png)
{: .center}
![](/assets/images/docker500.png)
{: .center}
![](/assets/images/docker2g.png)

## Atualizado em 15 de Março de 2018

À partir do JDK 8u131+ e JDK 9, existe um opção experimental da JVM que permite ao ergonomics a ler a memória do cggroups. Para habilitar estas opções, você precisa explicitamente informar o seguintes parâmetros

- -XX:+UnlockExperimentalVMOptions
- -XX:+UseCGroupMemoryLimitForHeap 

Você pode ver isto em ação no seguinte [Dockerfile](https://github.com/redhat-developer-demos/java-container/blob/master/Dockerfile.openjdk-cgroup#L5).

Agora vamos ver como estas opções se comportam. Execute:

~~~~
$ docker run -d --name mycontainer8g-jdk9 -p 8080:8080 -m 600M rafabene/java-container:openjdk-cgroup

$ docker logs mycontainer8g-jdk9|grep MaxHeapSize
size_t MaxHeapSize = 157286400 {product} {ergonomic}
~~~~

A JVM lê que o container está limitado a 600MB e cria uma JVM com a heap máxima de aproximadamente 150MV. Exatamente 1/4 da memória do container conforme especificado na [documentação](https://docs.oracle.com/javase/9/gctuning/parallel-collector1.htm#JSGCT-GUID-74BE3BC9-C7ED-4AF8-A202-793255C864C4).

## Atualizado em 21 de Abril de 2018.

O Java 10 foi lançado e agora possui todas as melhorias necessarias para ser executado dentro de um container. Por causa destas melhorias, as flgas

- -XX:+UnlockExperimentalVMOptions 
- -XX:+UseCGroupMemoryLimitForHeap* 

não são mais necessárias. De fato, se você tentar executar o JDK 10 com algum destes parâmetros habilitados, você verá o seguinte aviso: “Option UseCGroupMemoryLimitForHeap was deprecated in version 10.0 and will likely be removed in a future release.”

Por causa disto, o [Dockerfile para o JDK10](https://github.com/redhat-developer-demos/java-container/blob/master/Dockerfile.openjdk10) não possui nenhuma flag extra, ou qualquer outra configuração especial.

Execute a aplicação usando a imagem do JDK:

~~~~
$ docker run -it --name mycontainer -p 8080:8080 -m 600M rafabene/java-container:openjdk10
~~~~

Note que o comando

~~~~
“curl http://`docker-machine ip docker8192`:8080/api/memory”
~~~~

não falha mais e você verá a seguinte mensagem: “Allocated more than 80% (145.0 MiB) of the max allowed JVM memory size (145.0 MiB)%”. 145MB é 1/4 do limite de 600MB como definimos no container.

{: .center}
![](/assets/images/java-free.png)


## Conclusão

A JVM Java <del>até então não fornece</del> não fornecia suporte para entender que está sendo executada dentro de um container, e que alguns recursos como memória e CPU estavam restringido. Por caus disto, você não podia deixar o ergonomics da JVM tomar uma decisão sozinho relacionado ao tamanho máximo da heap.

Uma maneira de solucionar o problema é usar a imagem base do Fabric8 que é capaz de entender que está sendo executada em um container restrito e irá automaticamente ajustar o tamanho máximo da heap se você não o fizer você mesmo.

Outra solução é usar a [opção experimental](http://hg.openjdk.java.net/jdk9/jdk9/hotspot/rev/5f1d1df0ea49) que pode ser habilitada atrave's do parâmetro -XX:+UseCGroupMemoryLimitForHeap que foi incluido no JDK8u131 e JDK9 para suportar o limite de memória cgroups definido no container (ex. docker)

Esta postagem mostrou como uma JVM pode "explodir" à partir da perspectia de memória. Para mais post e notícias relacionadas ao desenvolvimento Java, siga-me no [twitter](http://twitter.com/rafabene). 