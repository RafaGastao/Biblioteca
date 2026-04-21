Sistema de Controle de Biblioteca
Descrição
Aplicação web desenvolvida em Java para gerenciamento de biblioteca, permitindo controle de usuários, livros e empréstimos.

Tecnologias utilizadas
Java 17
Spring Boot
Spring MVC
Thymeleaf (HTML)
MongoDB
Maven
Bootstrap (CSS)

Como executar o projeto (ambiente local)
Pré-requisitos
Java JDK 17 ou superior
Maven instalado
MongoDB (local ou Atlas)
IDE (NetBeans, IntelliJ ou VS Code)

Configuração do banco de dados

Configure a conexão no arquivo:
src/main/resources/application.properties
Exemplo (MongoDB local):
spring.data.mongodb.uri=mongodb://localhost:27017/biblioteca
Exemplo (MongoDB Atlas):
spring.data.mongodb.uri=mongodb+srv://usuario:senha@cluster.mongodb.net/biblioteca

Executando o projeto
No terminal, dentro da pasta do projeto:
mvn spring-boot:run
Ou execute diretamente pela IDE.
Acesso ao sistema

Após iniciar o servidor, acesse:
http://localhost:8080/login
Login padrão como (Administrador)
Usuário: admin  
Senha: 123
