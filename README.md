![](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white) ![](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white) ![](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)

# Aplicativo CS GO Matches
Este aplicativo é destinado a exibição de partidas de CS:GO a partir do dia atual. Todas as informações são consultadas da API [PandaScore](https://pandascore.co/), seguindo o fuso horário local do disposito no qual o app foi instalado.

## Implementação
O App foi desenvolido utilizando o padrão de design Model-View-ViewModel (MVVM), utilizando os conceitos do Single-Activity, com diversas bibliotecas do Android Jetpack como, por exemplo, Navigation Component, ViewModel e Paging, além de padrões de design como Use Case, Repository e Data Source. Além disso, este app utiliza injenção de dependência com Hilt e possui testes locais e instrumentados (com Espresso). Visando construir uma arquitetura escalonável, desacoplada e limpa,  o app foi desenvolvido com 4 camadas em 3 níveis, dispostas da seguinte forma:

**Domain**: Essa é a camada de nível mais alto, armazenando os modelos das Regras de Negócio e da aplicação, contendo também a implementação dos Use Cases e definição do Repository. A camada Domain está no topo das camadas porque ela representa as regras de negócio primordiais e mais sensíveis para a aplicação, sendo menos provável mudanças em sua estrutura com as eventuais manutenções.

**Infra**: Essa camada está um nivel abaixo da Domain, dando suporte a ela, implementando o Repository e definindo os contratos do Data Source usados no Repository para que ele possa processar e adaptar os dados externos.

**External**:
Ela implementa o Data Source, responsável pelos acessos externos, local ou remoto (neste app, utilizamos apenas o remoto). Esta camada é a de nível mais baixo pois armazena tudo aquilo que terá uma maior chance de ser alterado em futuras manutenções, sem a necessidade de intervenção no projeto.

**User Interface - UI**: Esta camada se encontra no mesmo nível da camada External. Trata-se de uma camada com grandes chances de alterações, considerando que ela é responsável pela parte gráfica e interação com o usuário. Os serviços (Services), que são componentes atrelados ao Sistema Operacional Android (que deve ser iniciado, no caso desta arquitetura, diretamente de fragmentos), também podem ser definidos nesta camada (neste app, não utilizamos services).

## Funcionalidades

#### Splash Screen
Exibe uma logo em full screen, com duração de 2 segundos.

#### Tela Inicial
Exibe a listagem das partidas de CS:GO a partir do dia atual, ordenadas por data. Além das demais informações contidas nos cards, cada partida contém os status ***Encerrada**** (partida encerrada), ***Agora**** (ocorrendo no atual momento) e ***Programada*** (exibindo uma data futura). As partidas programadas podem ser exibidas de duas formas distintas: com o dia da semana e hora para aquelas que ocorrerão até 1 semana contados a partir do dia atual; e com a data e hora para partidas datadas com mais de 1 semana.

#### Tela de Detalhes
Exibe os detalhes da partida em questão, com fotos, nomes, nickname dos jogadores de cada time, além da data e horário da partida.

## Projeto no Figma

	https://www.figma.com/file/OeNVxV2YkHXMgzky8YNQQO/Desafio-CSTV?node-id=0%3A1

## Screeshots

<p align="center">
  <img width="362" height="784" src=img/SplashScreen.jpeg>
</p>

<p align="center">
  <img width="362" height="784" src=img/HomeScreen.jpeg>
</p>

<p align="center">
  <img width="362" height="784" src=img/DetailsScreen.jpeg>
</p>