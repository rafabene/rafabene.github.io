---
title: Do you manage your dotfiles ?
---

When I started to develop using Javascript languages (AngularJS and NodeJS), the first step was to prepare the development environment in my Computer.

Of course I've tried several editors, but the nature of the project asks much more about a good text editor and console tools than a IDE *(Integrated Development Environment)*. After some reasearch, I was convinced by my brother [Frederico Benevides](https://br.linkedin.com/in/fredericobenevides/pt) that "One size fits all" development environment is based on VIM + TMUX.

I couldn't believe that VIM could be productive for develpoment! OMG!!! VIM!!??! - Take a look on [the following video about VIM + TMUX](https://www.youtube.com/watch?v=5r6yzFEXajQ) and make your own conclusions. I'll make a blog post about them in the future, so please, subscribe.

After customizing all my development environment I had a lot of files in my *~/.(home folder)* and then I needed:

 - Backup the environment
 - Share the environment between my personal laptop and Red Hat's laptop
 - Learn from the community environments
 - Share what I've learned with them.
 
To resolve such issue, my brother said that he created a Ruby project that contains all his ~/.([dotfiles](https://github.com/fredericobenevides/dotfiles)) available. A quick look for the term *"dotfiles"* and I was able to find the following page: [https://dotfiles.github.io/](https://dotfiles.github.io/) - *Your unofficial guide to dotfiles on GitHub*. This page has a link to several repositories that contains *dotfiles* from their authors. Most of them have different scripts that automates the installation of their prefered tools on your (Linux or Mac) based computer. 

Due to my recent incursion in the "DevOps" practices, I decided to create my own dotfiles project using [Ansible playbooks](http://docs.ansible.com/ansible/playbooks.html) to deploy and configure my ~/. environment. 

Actually I keep configurations about:

- [VIM](http://vimawesome.com/)
- [TMUX](https://tmux.github.io/)
- [ZSH](http://www.zsh.org/)
- [Oh-My-Zsh](http://ohmyz.sh/)
- [Global gitignore](http://technicalpickles.com/posts/configure-git-to-globally-ignore-some-files/)

and it supports

- Mac with [homebrew](http://brew.sh/)
- Linux [Fedora](https://getfedora.org/) 22+

To give it a try, 

 1. [fork my dotfiles project](https://github.com/rafabene/dotfiles),
 2. Clone it to your computer with  
 ```
 git clone https://github.com/YOUR_USER_NAME/dotfiles.git
 ```
 3. [Install ansible](http://docs.ansible.com/ansible/intro_installation.html)
 4. Run ```./bootstrap.sh```
 5. Enjoy the new console and TMUX with VIM
 6. Customize it to match your needs
 
Take a look on the result:
 
  ZSH + Oh-my-zsh + [Agnoster theme](https://gist.github.com/agnoster/3712874)
  
  ![](https://cloud.githubusercontent.com/assets/2618447/6316862/70f58fb6-ba03-11e4-82c9-c083bf9a6574.png)
 
  TMUX + VIM
  
  ![](/assets/images/tmuxvim.png)
  
I'll cover more details about the installed environment in the next posts.
 
 