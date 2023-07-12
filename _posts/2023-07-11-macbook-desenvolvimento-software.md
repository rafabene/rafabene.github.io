---
title: Como utilizo o Macbook para desenvolver Software
header:
    teaser: /assets/images/macbook.png
tags: [macbook, java, mac, desenvolvedor, dev]
---

Estes dias resolvi formater meu notebook, um "velho" Macbook Pro 2019, que trouxe de mudança dos EUA. Não vou entrar no mérito do preço proibitivo dos Macbooks no Brasil, mas sem dúvidas alguma, o conjunto Hardware + Sistema Operacaional (MacOS) de um Macbook Pro, é na minha opinião um casamento perfeito para desenvolvedores.

Para quem gostaria de saber o motivo de eu utilizar o Mac, aqui vão algumas das minhas justificativas: O Sistema Operacional é estável, com suporte a grande maioria dos softwares, e com o Shell baseado em Posix. Para mim, é a garantia perfeita de produtividade! Tudo simplesmente funciona!

E para quem já é usuário Mac, e gostaria de saber o que eu utilizo, resolvi fazer aqui uma lista do que considero ser os primeiros passos para ter um Macbook funcional para começar a produzir.

Vamos lá:

### - Instalação do [Microsoft Edge](https://www.microsoft.com/pt-br/edge)

Sinceramente, não gosto muito do Safari. Prefiro o Google Chrome, ou o Microsoft Edge. Venho utilizando o Microsoft Edge há algum tempo, e este se tornou meu navegador favorito por ter um consumo de memória levemente menor que o o Google Chrome. 

*Extensões*: [LastPass](https://lastpass.com/) - É o único que para mim é primordial, pois realmente não sei sequer uma senha minha. Tudo é gerenciado pelo LastPass, então para começar a trabalhar 

### - Remoção dos items não usados do Dock

O Dock do Mac vem por padrão com tanto aplicativo, que na minha opinião, só fazem atrapalhar. Gosto de deixar no Dock apenas item que uso TODAS AS HORAS. 

*O que eu deixo*: Finder e Launchpad, MS Edge, Terminal, Calendário, App Store, e Configurações.

### - Instalação do [iTerm2](https://iterm2.com/)

O iTerm2 é um excelente substituto do Terminal que vem no MacOS. Com várias funcinalidades que realmente são bem interessantes. Você pode ver quais são estas funcionalidades aqui: <https://iterm2.com/features.html>

### - Instalação do [Magnet](https://apps.apple.com/br/app/magnet/id441258766)

O Magnet é excelente para posicionar as janelas no seu ambiente. Como faço bastantes demonstrações para clientes, às vezes é necessário posicionar diversas janelas de uma forma que o cliente veja tudo que está acontecendo. O Magnet é perfeito para isto.

### - Instalação do [Scroll Reverser](https://pilotmoon.com/scrollreverser/)

Scroll Reverser é uma aplicação gratuita que reverte a direção de rolagem do mouse, independente dos ajustes do trackpad e do mouse (incluindo o Magic Mouse).

Gosto de deixar configurado para quando eu rolar ou mouse para baixo, a rolagem da página seja para baixo também.

### - Instalação do [Homebrew](https://brew.sh/)

O Homebrew é um gerenciador de pacotes para o MacOS. Pense no Homebrew como um apt-get ou Yum no Linux, ou o Chocolatey para o Windows. [Diversas ferramentas](https://formulae.brew.sh/formula/) podem ser instaladas com o Homebrew. Portanto é um item de primeira necessidade.

*Pacotes que geralmente instalo primeiro*: `ansible, stats, e terminal-notifier.`

Aliás, o [Stats](https://github.com/exelban/stats) é excelente para poder monitorar os recursos (CPU, Memória, Rede, Disco, etc) do seu MacOS rapidamente.

### - Instalação do ["Oh my ZSH"](https://ohmyz.sh/)

O "Oh my ZSH" permite a customização (via temas e plugins) do zsh (Z Shell) de forma que sua produtividade será consideravelmente aumentada.

São mais de [300 plugins disponíveis](https://github.com/ohmyzsh/ohmyzsh/wiki/Plugins), além de uma série de [temas](https://github.com/ohmyzsh/ohmyzsh/wiki/Themes).

O tema que eu utilizo é o ["agnoster"](https://github.com/ohmyzsh/ohmyzsh/wiki/Themes#agnoster) que requer a instalação de algumas [fontes do Powerline](https://github.com/powerline/fonts).

### - Instalação do [OBS (Open Broadcaster Software)](https://obsproject.com/pt-br)

O OBS é bastante conhecido no meio das pessoas que criam vídeos para o Youtube. É uma ferramenta opensource que permite a criação de cenários que contenham cameras, disposição de elementos e outras funcionalidades que simplesmente não dá para ficar sim. 

No meu caso, é a ferramenta que utilizo para gravar vídeos. Já deu uma olhada no canal [DevX Time](http://youtube.com/rafabene)?

### - Instalação do [Google Drive](https://drive.google.com/) e do [Dropbox](https://www.dropbox.com/)

Tanto o Google Drive quando o Dropbox possuem aplicativos que quando instalados, permitem a integração deles com o `Finder`. Esta integração é interessante pois permite eu acessar (ou armazenar) facilmente os arquivos na nuvem, diretamente do meu desktop.


### - Instalação do [Parallels](https://www.parallels.com/br/) com Windows 11

O Parallels não só permite a execução do Windows em uma máquina virtual, mas também permite a integração entre os dois ambientes de uma forma que eu não vi em outros hipervisors.

Você deve estar se perguntando o por quê de ter uma máquina virtual Windows sendo que enauteci tanto o poder do MacOS e sua infinidade de softwares. Bem o fato é que também sou intusiasta de calibração de motores usando injeções programáveis como a [Fueltech](https://fueltech.com.br/). Infelizmente o software para gerenciamento da minha FT450 ([FTManager](https://fueltech.com.br/pages/software))só é disponibilizado para Windows.

### - Instalação do [Visual Studio Code](https://code.visualstudio.com/Download)

Aqui a coisa começa a ficar já bem mais interessante para desenvolvedores. Se você é um desenvolvedor, nem preciso me extender muito aqui para falar que hoje, o Visual Studio Code é o editor de código preferido da grande maioria de Devs.

Não vou conseguir aqui falar de todos os plugins que utilizo, mas já fica aqui a idéia para um novo post só para falar disto.

### - Instalação do [Podman](https://podman-desktop.io/)

Como um desenvolvedor que utiliza, divulga e defende a utilização de cointainers; além de ser usuário do Docker por vários anos, um ambiente que permite a execução de containers localmente, é imprescindível para mim.

Para usuários Mac, o Docker Desktop era uma solução factível (antes eu usava o docker-machine) para criar VMs que permitem a execução de containers Linux. Mas desde que o [Docker Desktop começou a ser pago](https://gago.io/blog/docker-desktop-pago/), e considerando que sou defensor e usuário também de Software Livre, fiquei maravilhado com o [Podman](https://podman.io/), e passei a adota-lo.

### - Instalação de ambiente Java usando o [SDKMan](https://sdkman.io/)

O SDKMan é uma espécia de gerenciador de Kits de desenvolvimento como o próprio nome diz (Software Development Kit Manager). O legal do SDKMan (especialmente para desenvolvedores Java) é a possibilidade ter ter várias versões diferentes do JDK, Maven, etc e utilizar versões específicas por projetos.

O SDKMan possui várias versões de diversas [distribuições de JDK](https://sdkman.io/jdks) (Correto, Oracle, OpenJDK, Zulu, etc). E também possui [diversas ferramentas](https://sdkman.io/sdks) (ant, maven, jmeter, visualvm, etc) que podem ser instaladas pelo SDKMan.

*Geralmente eu instalo*: `visualvm, quarkus, springboot, java, e maven`

### - Configuração de uma chave SSH

Por fim, se eu não tiver feito backup das minhas chaves privadas, é a hora de gerar um par de chaves públicas e privadas usando o comando `ssh-keygen`

Se você não tinha um par de chaves previamente gerados, agora é a hora de fazer o upload da sua chave pública para os sites que a utilizam. Um exemplo é o próprio [Github](https://github.com/settings/keys) 

## Conclusão

Esta lista de softwares está longe de atender 100% dos desenvolvedores, mas é a lista do que eu fiz para ter um Macbook funcional para o meu dia-a-dia.

E você? Usa viu algum software que você também usa no seu ambiente? Ou complementaria com algum outro? Deixe seu comentário.

Aproveite, e não deixe de dar uma olhada nas redes sociais: 

<a href="https://twitter.com/rafabene" itemprop="sameAs" rel="nofollow noopener noreferrer me">
    <i class="fab fa-fw fa-twitter-square" aria-hidden="true"></i><span class="label">rafabene</span>
</a>

<a href="https://instagram.com/rafabene.devx" itemprop="sameAs" rel="nofollow noopener noreferrer me">
    <i class="fab fa-fw fa-instagram" aria-hidden="true"></i><span class="label">rafabene.devx</span>
</a>

Abraços!