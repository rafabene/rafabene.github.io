---
title: Dicas para iniciantes Java em Projetos Corporativos
---

Recentemente minha célula de codificação ficou encarregada de refatorar um sistema crítico em todos os sentidos: O Cliente possui grande expectativa no produto; O Sistema é composto por vários módulos e é grande... Muito grande...; A cifra investida no sistema é muito alta; Todos os módulos estão sendo desenvolvidos há 2 anos por um grande player do mercado.


Não posso dar maiores detalhes sobre o caso devido a questões éticas, mas o fato é que o sistema como um todo sofreu todos os tipos de maldições possíveis nestes últimos dois anos de desenvolvimento: Houve um grande "turnover" na equipe (Gerente de projetos, Analistas de requisito, Desenvolvedores, Projetistas e até o gerente (gestor) da área informatizada). A consequência disto foram incontáveis mudanças de requisitos, mudanças de protótipo, mudanças na base de dados, enfim!


Realmente o projeto teve tudo para dar errado até que recebeu um golpe final: Um dos Módulos do Sistema foi aceito (o desenvolvimento do Sistema foi terceirizado, lembram-se?) com uma verificação pobre e imatura de nossa parte (Tudo bem, confessamos o erro!) e o resultado: O Sistema possuia tanto remendo que simplesmente não funcionou quando os dados foram importados.


Então minha equipe foi chamada para dar um parecer sobre o código que foi "comprado". O Resultado foi unânime: Só dava para aproveitar o código das telas. O resto deveria ser jogado fora e desenvolvido novamente. "Como assim? Já gastamos X * Y neste projeto e não podemos joga-lo fora. Vamos concerta-lo!" - explicou o Gerente do Escritório de Projetos. E foi assim que a ideia deste Post nasceu


Ao examinar o código, fiquei impressionado com a quantidade de [POGs](http://desciclopedia.org/wiki/Programa%C3%A7%C3%A3o_Orientada_a_Gambiarras#Defini.C3.A7.C3.A3o_de_POG) que foram encontrados. Uma das pessoas da equipe alegou que a empresa contratada, com o objetivo de reduzir seus custos, contrata pessoas sem experiência em Java (louvável a iniciativa que dá a oportunidade do primeiro emprego a estes Jovens) e logo após um curso de 40 horas (louvável a iniciativa de treinar os "Padawans" :) ) até que jogam-lhes na "fornalha de uma célula de Codificação" E "aparentemente" sem nenhum direcionamento eles ficam lá esquecidos até ganharem experiência e alçarem seus próprios vôos (certamente em busca de outras empresas e melhores salários). Como acredito que toda pessoa que tem interesse e um bom direcionamento, consegue realizar verdadeiros prodígios espero listar abaixo algumas dicas baseadas nos "erros" encontrados no sistema que estou mantendo. Espero dar a minha contribuição para a Posteridade...


Para começar, o projeto em questão possui as seguintes camadas: Swing --> Business Delegate --> EJB Stateless Session Bean --> Dao Hibernate



- *Nunca retorne uma Collection nula*: Se existe um método com a seguinte assinatura  public List<Cliente> consultaClientesInadiplentes(), se a consulta não for retornar nenhum "registro", não retorne nulo, mas sim uma Collection vazia. Isto evitará vários NullPointerExceptions

- *Sempre ajuste o Fetch Size do Hibernate*: Quando realizar uma Query no Hibernate, procure sempre informar o "Fetch Size - setFetchSize(int fetchSize) ". Este método informará quantos registros devem ser trazidos por vez e evitará que o Hibernate faça 1 select para cada registro do Banco.

- *Sempre restrinja a quantidade máxima de registros (no servidor)*. Nunca deixe o cliente fazer um "Full Scan Table" na base de dados. Sempre restrinja a quantidade máxima de resultados para um valor ideal (gosto de usar 50 registros). Já ví casos de um EJB retornar um ArrayList de 1000 itens e o Cliente Swing verificava if (itens.size() > 50) mostra mensagem "Sua consulta retornou muitos resultados. Refine sua busca". É preciso explicar ?

- *Nunca esconda Exceptions*. Nunca, nunca mesmo esconda exceptions em blocos try/catch vazios.

- *Sempre empacote a exception recebida se for lançar outra*. Algumas vezes é necessário tratar uma excessão (IOException) e lançar outra no lugar (AlgumProblemaException). No construtor das Exceptions é possível informar um Throwable para ser empacotada, permitindo que não ser perca a causa da Exceptions que podem ser usados para Log, Debug, Diagnóstico ou melhor tratamento de erro. Ex: throw new AlgumProblemaException("Aconteceu um problema para exportar o arquivo", ioexception);

- *Apenas UnCheckedExceptions fazem rollback em um ambiente EJB/JTA/CMT*. Muitas pessoas utilizam o EJB para gerenciar as transações através do JTA/CMT, mas esquecem-se que apenas quando uma UnCheckedException ocorre é que o JTA faz o rollback da transação. Se alguma CheckedException for lançada, chame o método setRollbackOnly() do [UserTransaction](http://docs.oracle.com/javaee/5/api/javax/transaction/UserTransaction.html) para evitar da transação ser comitada

- *Não invente uma funcionalidade que já existe em Java*. Não foi nem uma ou duas vezes que vi um método que transforma String em Date ou vice-versa. Galera, existe o [SimpleDateFormat](http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html). Se quiserem que um botão seja Default em uma tela Swing, pelo amor de Deus, não coloquem actionPerformed em todos os campos, basta obter o RootPane do JFrame Swing e chamar o método [setDefaultButton(javax.swing.JButton)](http://docs.oracle.com/javase/6/docs/api/javax/swing/JRootPane.html#setDefaultButton(javax.swing.JButton))

- *Cuidado ao criar objetos dentro de laços* - Isto nem é Java, mas já vi muitos casos de Objetos (Daos, Formatters, etc) serem instanciados dentro de laços for

- *Cuidados com métodos estáticos (static)* - O excesso de métodos estáticos levam o código a uma tendência de programação procedural tornando métodos simples funções e não um comportamento do Objeto.

- *Cuidado com modificadores de visibilidade dos métodos*. Se um método é apenas utilizado por métodos de uma Classe, deixe-o como privado. A idéia é expor apenas a API das Classes. Pode parecer óbvio,mas vi muitos métodos expostos ao acaso. Não use protected porquê é bonitinho , se for extender a classe e precisar do método ou atributo (restrito a classe), marque-o como protected. Caso contrário, private nele.

- *Carregue os valores de domínio apenas uma vez*. Algumas coorporações possuem tabelas de domínios específicas e que raramente mudam. Este valores devem ser carregados na inicialização do sistema e armazenados localmente em alguma variável para ser utilizada em todos os JFrames. Imagine buscar (geralmente de um EJB) e carregar a todos os combos de uma tela, cada vez que esta é chamada...A Única tabela que geralmente não guardo é de município devido ao tamanho. Geralmente trago apenas os municípios da UF Selecionada.



Bom, como já está meio tarde e acho que me lembrei de tudo, espero ter contribuido um pouco com algumas dicas que gostaria de ter recebido de presente um dia. Se alguém gostou ou simplesmente quer contribuir, agradeceria se deixasse um recado.


Obrigado