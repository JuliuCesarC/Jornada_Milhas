# Challenge de Backend da Alura

https://github.com/JuliuCesarC/Jornada_Milhas/assets/106402577/2b1ffa9f-8618-4a0d-92a4-de9b3419e278

## About

A primeira parte do desafio consiste em construir uma api rest para uo site fictício **Jornada Milhas**, com o tema de viagens e venda de milhas. Foi disponibilizado no [Trello](https://trello.com/) alguns cards com as funcionalidades que a api deve ter e as metas para cada semana, sendo no total 4 semanas. A escolha das tecnologias utilizadas fica a critério de quem esta fazendo o challenge.

O intuito desse projeto é que o participante se desafie a trabalhar com ferramentas que ele ainda não domina ou nem sequer saiba, ao invés de ficar apenas no escopo daquilo que ja conhece.

## Build with

- <a href="https://www.java.com/"><img src="https://github.com/JuliuCesarC/Jornada_Milhas/assets/106402577/19526176-a931-4f85-8fa2-73371923ca5d" height=27/></a>
- [![Spring Boot][Spring]][Spring-url]
- [![Spring Doc][springdoc]][springdoc-url]
- [![MySql][mysql]][mysql-url]
- [![OpenAi][openai]][openai-url]

Venho utilizando as tecnologias Java e Spring Boot por algum tempo e tenho gostado bastante do resultado proporcionado por elas. Essa dupla possui uma enormidade de vantagens ao qual eu poderia destacar a excelente integração com os bancos de dados, a simplicidade para criar a aplicação, a segurança tanto do java como do Spring, a injeção de dependência, entre outros. Além de tudo isso, a linguagem java é mundialmente utilizada por diversas empresas, o que a torna uma excelente escolha para se especializar.

### OpenAi

Um dos desafios do projeto era implementar a api do ChatGpt na aplicação, para que os **textos de descrição do destino** caso não fossem preenchidos, a inteligencia artificial iria criar eles. Porem para que o cliente pudesse ter um controle maior, criei uma rota separada para essa tarefa, de forma que o usuário possa requisitar um texto e fazer alterações nele caso necessario (por experiencia própria sei que nem sempre o texto vem com uma qualidade legal), e se satisfeito, cadastre o destino com o texto revisado.

O processo de implementar o ChatGpt foi mais simples do que esperava. A OpenAi disponibiliza alguma bibliotecas para cada tipo de linguagem, e para a de java é a [openai-java](https://github.com/TheoKanning/openai-java) que foi feita pelo [TheoKanning](https://github.com/TheoKanning). Dentro do repositório temos alguns e exemplos e foi necessario apenas criar a classe de serviço [OpenAiGPTService](https://github.com/JuliuCesarC/Jornada_Milhas/blob/main/src/main/java/com/apiJornada/Milhas/domain/destination/OpenAiGPTService.java), e dentro dela colocar os códigos do tutorial com as alterações necessárias.

### Unit Test

Ao criar uma aplicação com o Spring Boot ele ja traz diversos pacotes de testes automatizados, como os pacotes **JUnit**, **Mockito**, **AssertJ**, entre outros. E com todas essas bibliotecas já previamente instaladas, temos um ambiente preparado para efetuar os testes, ao qual nesse projeto foi testado os dois controllers principais.

Os testes se mostraram muito eficientes não apenas para testar o código, mas também para verificar erros de logica no projeto, onde a forma de pensar para desenvolver o teste traz novas ideias e melhorias para o código original.

### Documentation

Outro beneficio do ecossistema do Spring é a disponibilidade de bibliotecas que automatizam tarefas que seriam muito trabalhosas, como por exemplo a **Spring Doc**, que cria uma documentação para api. No começo deste documento temos um video do site da documentação, que é muito util para ver quais rotas existem, o que cada uma exige e retorna, além de sempre que houver uma atualização na aplicação, a documentação se ajusta automaticamente.

## Getting Started

É preciso escolher uma IDE que consiga abrir e executar projetos java. Para quem deseja utilizar o Vs Code é necessário instalar o pacote [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack&ssr=false#overview).

1. Clonar o repositório

```bash
git clone https://github.com/JuliuCesarC/Jornada_Milhas.git
```

2. Configurar a conexão com o banco de dados **MySql**.

Dentro do pacote `src/main/resources` crie o arquivo `application.properties` e insira as seguintes configurações:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_database
spring.datasource.username=nome_do_usuario
spring.datasource.password=senha_do_usuario
```

3. Iniciar a aplicação

Cada IDE possui uma forma diferente para executar a aplicação, mas se configurado o database e o usuário correto, a api deve funcionar normalmente. Após executar a aplicação é possível acessar a documentação através do link `http://localhost:8080/swagger-ui/index.html` ou efetuar alguma requisição por exemplo do tipo *GET* para o url `http://localhost:8080/destinos`.

> Lembrando que como o banco de dados estará vazio, sera retornando apenas o objeto do tipo *Pageable*.

[Spring]: https://img.shields.io/badge/Spring-467328?style=for-the-badge&logo=spring
[Spring-url]: https://spring.io/projects/spring-boot
[springdoc]: https://img.shields.io/badge/SpringDoc-2A4016?style=for-the-badge&logo=openapiinitiative
[springdoc-url]: https://springdoc.org/
[mysql]: https://img.shields.io/badge/mysql-BF720D?style=for-the-badge&logo=mysql
[mysql-url]: https://www.mysql.com/
[openai]: https://img.shields.io/badge/OpenAi-251752?style=for-the-badge&logo=openai
[openai-url]: https://openai.com/
