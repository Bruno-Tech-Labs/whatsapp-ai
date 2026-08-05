# 🤖 BrunoTech Whatsapp-AI

Uma aplicação de automação e atendimento inteligente para WhatsApp, construída com Java, Spring Boot e Inteligência Artificial.

O **BrunoTech AI** nasceu como um projeto pessoal com o objetivo de explorar, na prática, como integrar **WhatsApp, APIs, backend, Inteligência Artificial e serviços em nuvem** para criar uma solução que possa ajudar pequenos negócios a automatizar tarefas e interações do dia a dia.

> 🚧 Projeto em desenvolvimento.

---

## 💡 A ideia

A proposta do BrunoTech AI é transformar o WhatsApp em uma interface simples para que pequenos negócios possam interagir com seus próprios dados e automatizar tarefas através de uma conversa.

A ideia é que, futuramente, seja possível utilizar o sistema para tarefas como:

- 💬 Atendimento e respostas automatizadas
- 🤖 Interação com Inteligência Artificial
- 📅 Agenda e lembretes
- 💰 Registro e consulta de pagamentos
- 📄 Geração de orçamentos e documentos em PDF
- 📊 Consulta de informações do negócio
- 🔔 Notificações e lembretes automáticos
- 🧠 Memória e contexto das conversas
- 🔐 Autenticação e controle de usuários
- 👥 Suporte a múltiplos clientes

A arquitetura será construída de forma que a IA não seja apenas responsável por conversar, mas possa futuramente **interpretar intenções e acionar funcionalidades reais do sistema**.

---

## 🏗️ Arquitetura

O projeto está sendo desenvolvido para trabalhar inicialmente com o seguinte fluxo:

```text
                    ┌──────────────────┐
                    │     WhatsApp     │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │  Meta Cloud API  │
                    └────────┬─────────┘
                             │
                         Webhook
                             │
                             ▼
                    ┌──────────────────┐
                    │   Spring Boot    │
                    │      API         │
                    └────────┬─────────┘
                             │
                  ┌──────────┴──────────┐
                  │                     │
                  ▼                     ▼
          Processamento             Inteligência
           de eventos               Artificial
                  │                     │
                  └──────────┬──────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │  Regras de       │
                    │     negócio      │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │ PostgreSQL /     │
                    │ outros serviços  │
                    └──────────────────┘
````

O projeto ainda está em evolução e essa arquitetura poderá ser expandida conforme novas funcionalidades forem implementadas.

---

## 🛠️ Tecnologias

### Backend

* **Java 17**
* **Spring Boot**
* **Spring Web**
* **Maven**

### Banco de dados

* **PostgreSQL**

### Integrações

* **WhatsApp Cloud API**
* **Meta Graph API**
* APIs de Inteligência Artificial

### Infraestrutura

* **Docker**
* **Render**
* **GitHub**

### Ferramentas

* **IntelliJ IDEA / VS Code**
* **Postman**
* **Git / GitHub**

---

## 🔐 Segurança

A integração com o WhatsApp utiliza Webhooks da Meta.

O projeto implementa validação da assinatura `X-Hub-Signature-256` utilizando **HMAC-SHA256**, garantindo que as requisições recebidas pelo webhook possam ser verificadas antes de serem processadas.

Informações sensíveis, como tokens e secrets, são mantidas através de **variáveis de ambiente** e não fazem parte do código-fonte.

---

## 🧪 Status atual

O projeto está sendo desenvolvido de forma incremental.

### Concluído

* [x] Criação da aplicação na Meta for Developers
* [x] Configuração do WhatsApp Cloud API
* [x] Configuração do número WhatsApp Business
* [x] Implementação do webhook
* [x] Handshake de verificação com a Meta
* [x] Recebimento de mensagens reais
* [x] Validação de `X-Hub-Signature-256`
* [x] Integração com a API de envio de mensagens
* [x] Deploy da aplicação utilizando Docker
* [x] Deploy do backend no Render

### Em desenvolvimento

* [ ] Integração com modelo de Inteligência Artificial
* [ ] Processamento inteligente das mensagens
* [ ] Memória e contexto das conversas
* [ ] Persistência de dados
* [ ] Autenticação
* [ ] Agenda
* [ ] Pagamentos / Pix
* [ ] Geração de PDFs
* [ ] Regras de negócio
* [ ] Suporte a múltiplos clientes
* [ ] Melhorias de observabilidade e monitoramento

---

## 🤖 IA como ferramenta de desenvolvimento

Um dos objetivos deste projeto também é explorar uma forma mais moderna de desenvolvimento utilizando **Inteligência Artificial como aliada durante o processo de construção**.

A IA está sendo utilizada para acelerar o desenvolvimento, mas não com o objetivo de simplesmente gerar código e copiá-lo.

Estou utilizando a IA para:

* explorar diferentes soluções;
* questionar decisões de arquitetura;
* entender tecnologias e conceitos novos;
* investigar erros e problemas;
* comparar abordagens;
* revisar implementações;
* aprender o motivo de cada decisão tomada.

A intenção é utilizar a velocidade proporcionada pela IA sem abrir mão da compreensão do que está sendo construído.

Por isso, este projeto também funciona como um **laboratório prático de aprendizado**, onde cada nova funcionalidade é uma oportunidade para aprofundar conhecimentos em backend, APIs, segurança, arquitetura, cloud e Inteligência Artificial.

---

## 👨‍💻 Sobre o criador

Meu nome é **Bruno Rijo**, sou formado em **Sistemas de Informação** e pós-graduado em **Engenharia de Software** e **Arquitetura de Software**.

Atuo na área de desenvolvimento de software e atualmente estou direcionando meus estudos e projetos pessoais para o desenvolvimento **Backend com Java e Spring Boot**, além de aprofundar meus conhecimentos em APIs, arquitetura de software, cloud e Inteligência Artificial.

O BrunoTech AI é um projeto pessoal criado para transformar esses estudos em prática, permitindo experimentar tecnologias e conceitos em uma aplicação que evolui continuamente.

### 🎯 Objetivos com o projeto

Além de desenvolver uma solução que possa futuramente ser utilizada por pequenos negócios, o projeto tem como objetivos:

* aprofundar meus conhecimentos em Java e Spring Boot;
* praticar arquitetura e desenvolvimento de APIs;
* trabalhar com integrações externas;
* aprender mais sobre Webhooks;
* explorar segurança de APIs;
* trabalhar com Docker e Cloud;
* entender aplicações práticas de Inteligência Artificial;
* experimentar arquiteturas capazes de evoluir para múltiplos clientes.

---

## 🚀 Próximos passos

O projeto está apenas começando.

A próxima grande etapa é integrar Inteligência Artificial ao fluxo de mensagens e evoluir gradualmente de um sistema capaz de **receber e enviar mensagens** para uma aplicação capaz de **entender solicitações e executar ações reais**.

A ideia é construir cada etapa, testar, questionar e aprender com o processo.

---

## 📌 Projeto

**BrunoTech AI**
Desenvolvido por **Bruno Rijo**

Este projeto está em desenvolvimento e seu código está disponível para fins de estudo, experimentação e evolução contínua.

⭐ Se você achou o projeto interessante, fique à vontade para acompanhar sua evolução!

```

### Uma coisa que eu mudaria no seu README atual

Eu **não venderia ainda o projeto como "IA para pequenos negócios" como se já fosse um produto pronto**. A história mais interessante neste momento é:

> **"Estou construindo uma aplicação real enquanto aprendo na prática como conectar WhatsApp, backend, APIs, cloud e IA."**

Isso combina perfeitamente com o que você quer comunicar no LinkedIn e, principalmente, **é verdadeiro sobre o estágio atual do projeto**.

E quando você adicionar a IA, banco, autenticação, agenda, Pix etc., vamos atualizando o README conforme o projeto evolui.
```
