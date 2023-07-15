# DAC API Rest 2023
## Descrição
REST Api em JAVA para a turma de DAC do primeiro período de 2023.
Esta API tem como objetivo mostrar um exemplo de uma API Rest que persiste dados em um banco MYSQL

## Como funciona
Para rodar o projeto é necessário ter o VSCode instalado e estar utilizando o Java 17. Com isso o vscode identificará a configuração já pronta para apenas clicar no "play" e conseguir rodar o projeto.
O script para montar as tabelas encontra-se na pasta raíz do projeto em bd-script.sql e é necessário rodá-lo para as persistências funcionarem sem problemas.

## Git Flow
Como padrão, utilizaremos a branch "main" como o ambiente de produção e "develop" como ambiente de homologação onde apenas no início é permitido commitar direto nela. O fluxo a seguir será o GitFlow, como descrito na imagem a seguir:
![GitFlow](https://codigomaromba.files.wordpress.com/2019/01/gitflow-1.png)
Para testes a feature deve ser testada na branch da própria feature, e assim que liberada pelo teste esta deve ser integrada à develop através de um Pull Request

## Pull Request
O Pull Request deve ser feito da branch da feature para a develop, apenas.
Para aceitar deve completar os seguintes requisitos:
- Deve ter sido aprovado por pelo menos um membro do grupo
- Deve ser aceito por alguém diferente que aprovou primariamente, sendo assim necessário pelo menos duas pessoas participando do processo
- Quando houver uma versão estável na develop, um Pull Request deve ser feito da develop para a main.
