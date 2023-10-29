```txt
_   _       _   ___  _     _  __
| | | |     (_) /   || |   (_)/ _|
| | | |_ __  _ / /| || |    _| |_ ___
| | | | '_ \| / /_| || |   | |  _/ _ \    :: Version ::                    (v1.0.0)
| |_| | | | | \___  || |___| | ||  __/    :: Spring Boot ::                (v2.4.3)
\___/|_| |_|_|   |_/\_____/_|_| \___|

```

## Documentação:

Esse projeto foi desenvolivdo com o objetivo para expor serviços necessários, para o funcionamento
da aplicação Uni4Life uma rede social para os alunos e professores da Unisinos.

Autores:

 - Gustavo Henrique Lamb
 - Gregory Stein
 - Luan Alves Genro

## Swagger

Abaixo segue o link para acessar o swagger da aplicação, lembrando que para acessar a aplicação precisa estar 
rodando pelo Intelij ou algum container:

```txt
http://localhost:{ALGUMA_PORTA}/uni4life/swagger-ui.html
```
Deve aparecer uma imagem assim:

![swagger-ui](doc/swagger-ui.png "Exemplo da tela inical do swagger.")

## Autenticação
Objetivo dessa sessão é explicar como se autenticar na API com algum usuário, visto que o endpoint de autenticação 
não é documentado no swagger. Com a aplicação rodando em `{ALGUMA_PORTA}` Basta executar o `curl` abaixo subisituido os 
trecho `
{EMAIL_USUARIO}` e `{SENHA_USUARIO}`

````curl
curl --location --request POST 'http://localhost:{ALGUMA_PORTA}/uni4life/oauth/token' \
--header 'Authorization: Basic dW5pNGxpZmUtY2xpZW50OnVuaTRsaWZlLXNlY3JldA==' \
--form 'grant_type="password"' \
--form 'scope="app"' \
--form 'username="{EMAIL_USUARIO}"' \
--form 'password="{SENHA_USUARIO}"'
````

Esse endpoint vai fornecer um Bearer Token, com tempo de expiração de uma hora. Esse token sera necessário passar no 
header `Authorization` para todos os endpoints que necessitam de autenticação. Exemplo de response do endpoint:

```json
{
    "access_token": "ciUGPtUQoz4fc/qp/oDjJpat4fI=",
    "token_type": "bearer",
    "expires_in": 3599,
    "scope": "app"
}
```

## Docker

Objetivo dessa sessão é explicar como gerar a imagem para criação de um container docker para rodar
a aplicação caso necessário. Claramente para o funcionamento dos comandos abaixo é pré requisito
que o **Docker** esteja instalado na máquina.

#### Passo 1: Build (Opcional)
Caso não tenha acesso ao repositório da imagem no docker hub(_Obs: Não deve acontecer pois o repositório é público_),
é possível realizar a construção da imagem de forma local atráves do seguinte comando:

```docker
docker build --tag=frostte32/uni4life:latest .
```

Lembrando que esse passo é opcional, serve só para caso a imagem do repositóro esteja desatualizada e seje 
necessário utilizar uma versão mais novo, Ou caso não tenha acesso. **Observação:** Esse comando precisa ser 
realizado na pasta raiz da api 
onde esta localizado o arquivo **Dockerfile**

#### Passo 2: Subindo o container

Primeiramente é necessário baixar a imagem docker da aplicação do repostório do docker hub, para isso é necessário 
rodar o comando abaixo:

```docker
docker pull frostte32/uni4life
```

Depois disso basta criar o container utlizando o `docker run`, lembrando que a aplicação por padrão expõem a porta 
8080 então é necessário fazer o bind de `{ALGUMA_PORTA}` da máquina host com a porta exposta do container. Exemplo 
de comando:

```docker
docker run -d --name uni4lifeApi -p {ALGUMA_PORTA}:8080 frostte32/uni4life
```

A aplicação também se conecta a um banco de dados, caso queira  também é possível definir um `{BD_USERNAME}` e uma 
`{BD_PASSWORD}` customizada para o container atráves de variavéis de ambiente. Exemplo de comando

```docker
docker run -d --name uni4lifeApi -e BD_USERNAME={BD_USERNAME} -e BD_PASSWORD={BD_PASSWORD} -p {ALGUMA_PORTA}:8080 
frostte32/uni4life
```

Lembrando que isso é opcional, caso não inforamdo o valor padrão do usuário e da senha vai ser `root` e `root`

#### Passo 3: Atualizando a imagem (Opcional)

Esse é mais um passo opcional, apenas sendo necessário para o dono do repositório lembrar os comandos para 
atualizar a imagem. Primeiro é necessário fazer o build da imagem:

```docker
docker build --tag=frostte32/uni4life:latest .
```

Após isso basta realizar o push da imagem com o seguinte comando:

```docker
docker push frostte32/uni4life:latest 
```

#### Comandos úteis:

Alguns comandos que podem ser úteis:

- Parar a execução do container

```docker
docker stop uni4lifeApi
```
- Inicar a execução do container

```docker
docker start uni4lifeApi
```

- Excluir o container

```docker
 docker container rm uni4lifeApi
```

- Excluir a imagem
```docker
 docker image rmi frostte32/uni4life
```

## H2 Database

Objetivo dessa sessão e explicar como utilizar o nosso banco de dados em mémoria o H2 Database, que hoje estamos 
utilizado para testes dos endpoints enquanto elaboramos um script otimizado para nosso banco MySQL. Lembrando que 
para executar os passos abaixo é necessário que API esteja rodando no InteliJ ou em algum container

#### Passo 1: Acessando o banco

Com a aplicação rodando, simplismente acesse pelo seu browser o endereço:

```txt
http://localhost:{ALGUMA_PORTA}/uni4life/banco
```
Lembrando que a porta pode variar de acordo com a porta exposta no container, caso não esteje rodando em um 
container a porta padrão é `8080`. Quando acessar deve aparecer uma tela assim.

![login-h2](doc/login-h2.png "Tela de login do H2.")

Preencha os campos necessários com os seguintes valores:

- Driver Class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:uni4life`
- User Name: `root` **OBS:** Esse é o valor padrão, se não foi informado a varíavel de ambiente `{BD_USERNAME}`
- Password: `root` **OBS:** Esse é o valor padrão, se não foi informado a varíavel de ambiente `{BD_PASSWORD}`

Após isso basta clicar no botão "Connect", que  deverá então aparecer a tela abaixo e você estara logado:

![tele-inicial-banco](doc/tela-inicial-banco.png "Tela inicial do banco H2 Database.")

#### Passo 2: Rodando comandos SQL

Uma explicação rápida da tela inicial, no lado esquerdo da tela tem todas as tebelas criadas pela aplicação de 
acordo com a definição das classe Enitity `domain.entity`. No lado direito tem um quadro branco, para inseri 
comandos SQL, ao clicar no botão "Run" os comandos são executados e o resultado aparece logo abaixo:

![exemplo-query](doc/exemplo-query.png "Exemplo para executar as Query.")

