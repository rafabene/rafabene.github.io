---
title: 10 coisas para se evitar em contêineres docker
---

This post was originally posted on [Red Hat Developers](https://developers.redhat.com/blog/2016/02/24/10-things-to-avoid-in-docker-containers/) and this version is a translation to Portuguease.


Então finalmente você se rendeu a utilização de contêineres e descobriu que eles resolvem vários problemas e possui uma série de vantagens:

**Primeiro: Contêineres são imutáveis** - O Sistema Operacional, bibliotecas, versões, configurações, pastas, e aplicação são todos empacotados dentro de um contêiner. Você tem a garantia que a mesma imagem que foi testada no ambiente de "QA" irá ser implantada e executada em produção com o mesmo comportamento.

**Segundo:  Contêineres são leves** - A utilização de memória de um contêiner é pequena. Ao invés de dezenas ou centas de megabytes, o contêiner irá somente alocar memória para o processo principal.

**Terceiro: Contêineres são rápidos** - Você pode inicializar um contêiner tão rapidamente como um processo típico do Linux. Ao invés de minutos, você pode inicializar um contêiner em alguns segundos.

Entretanto, muitos usuários ainda tratam contêineres como uma "máquina virtual" e esquecem que contêiner tem uma característica muito importante: **Contêineres são descartáveis**.

O grande "mantra" de contêineres é: 
{: .text-center}

***"Contêineres são efêmeros”***.
{: .text-center}

![](/assets/images/rh_icon_container_with_app_flat.png)
{: .text-center}

Esta característica força os usuários a terem uma percepção diferente sobre a forma de como eles deveriam utilizar e gerenciar contêineres; e eu vou explicar o que você **NÃO** deve fazer para continuar extraindo os melhores benefícios de um contêiner.

- 1) **Não armazene dados dentro de um contêiner** - Uma aplicação executando a versão 1.0 deve ser facilmente substituída pela versão 1.1 sem algum impacto or perda de dados. Por este motivo, se você precisar armazenar dados, faça-o em um volume. Neste caso, você também deve ter o cuidado caso mais de um contêiner escreva dados no mesmo volume já que isto poderia corromper os arquivos. Tenha a certeza que suas aplicações foram projetadas para escrever dados um sistema de arquivos compartilhado.

- 2) **Não distribua sua aplicação em duas partes** - Como algumas pessoas enxergam contêineres como "máquinas virtuais", a maioria deles têm a tendência de pensar que eles deveriam implantar suas aplicações em contêineres que estejam previamente sendo executados. Isto até pode ser verdade durante a fase de desenvolvimento onde você precisa fazer o deploy e o debug dentro do container; mas para um pipeline de continuous delivery (CD) para o "QA" e para a produção, a sua aplicação já deve fazer da imagem. Lembre-se: Contêineres são imutáveis. 
 
- 3) **Não crie imagens grandes** - Uma imagem grande será mais difícil de distribuir. Tenha a certeza que você tem apenas os arquivos e bibliotecas necessárias para executar sua aplicação/processo. Não instale pacotes desnecessários, ou execute "updates" (yum update) que faça o download de vários arquivos uma uma nova camada da imagem.

- 4) **Não use uma imagem com uma única camada** - Para um uso efetivo do sistema de arquivos em camada, sempre crie a sua própria imagem com o Sistema Operacional, outra camada para a definição do usuário, outra camada para instalação do runtime, outra camada para a configuração, e finalmente outra camada para a sua aplicação. Isto fará com que seja mais fácil recriar, gerenciar e distribuir a sua image.

- 5) **Não crie imagens à partir de contêineres em execução** - Em outras palavras, não use "docker commit" para criar uma imagem. Este método de criar imagens não é "reproduzível" e deve ser completamente evitado. Use sempre um arquivo *Dockerfile* ou qualquer outro método, como o S2I (source-to-image) que é totalmente reproduzível, e você pode ainda acompanhar as mudanças no arquivo *Dockerfile* se você armazena-lo em um repositório de controle de versões (git).

- 6) **Não use apenas a tag "latest"** - A tag "latest" está para o docker, assim como a versão  "SNAPSHOT" está para usuários Maven. O uso de tags é encorajado por causa da natureza do sistema de arquivos em camadas dos contêineres. Você não quer ter surpresas quando você constrói uma imagem, e alguns meses mais tarde você descobre que sua aplicação não consegue executar por quê a imagem "base" (FROM no Dockerfile) foi substituída por uma nova versão que não é compatível, ou por quê a versão "latest" foi obtida do "build cache". A tag "latest" deve também ser evitada quando for executar contêineres em produção pois isto evitaria saber qual a versão da imagem que está sendo executada.

- 7) **Não execute mais de um único processo em um único contêiner** - Contêineres são perfeitos para executar um único processo (daemon http, servidor de aplicações, banco de dados, fat jars, etc), mas se você tem mais de um único processo, você terá mais problemas em gerência-los, obter os logs, atualiza-los , etc; se estiverem em um único contêiner. 

- 8) **Não armazene credenciais na imagem.** - Você não quer informações sensíveis como nome do usuário e senha armazenados dentro da sua imagem. Use variáveis de ambiente ou "secrets" para obter esta informação de fora do contêiner. Para mais detalhes leia o artigo [From env variables to Docker secrets](https://medium.com/lucjuggery/from-env-variables-to-docker-secrets-bc8802cacdfd)

- 9) **Não execute processos com o usuário root"** - *"Por padrão, contêineres docker são executados como root . (...) À medida que o docker amadurece, outras opções de segurança padrão podem se tornar disponíveis. Atualmente, execuções como root é perigoso e pode não estar disponível em todos os ambientes. Suas imagens deve usar a instrução USER para especificar um usuário não root para a execução do contêiner."* (Tirado do [Guidance for Docker Image Authors](http://www.projectatomic.io/docs/docker-image-author-guidance/))

- 10) **Não depende de endereços IP** - Cada contêiner possui seu próprio endereço IP interno e este será alterado a cada execução, ou até mesmo se você reinicilizar o contêiner. Se sua aplicação ou microserviço precisa comunicar com outro contêiner, use varíaveis de ambiente para passar o **nome** do host e **porta** de um contêiner para o outro.

Espero que tenha gostado destas dicas. Aproveito a oportunidade para divulgar o canal que criei chamado ["DevX Time"](https://www.youtube.com/user/rafabene). Este canal foca no desenvolvimento de aplicações cloud-native em Java utilizando contêineres. 

Se quiser aprender sobre Docker, não perca o primeiro vídeo da série:

<iframe width="560" height="315" src="https://www.youtube.com/embed/N_jfTUXazGU" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

Os demais vídeos da ["playlist"](https://www.youtube.com/watch?v=N_jfTUXazGU&list=PLYGlbS_vYmdYLf-T71JVD-5kDL1onU202) aprofundam no assunto. Subscreva no canal para ficar à par dos próximos vídeos que focarão em Kubernetes, práticas DevOps, OpenShift, Microserviços.

Siga-me também no twitter para ficar à par das últimas novidades no mundo Java: <https://twitter.com/rafabene>

Um abraço a todos!