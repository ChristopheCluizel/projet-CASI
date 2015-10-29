Proposition Projet CASI
===

## Big data and tweet streaming

### Groupe

- Thibaud Dauce
- Christophe Cluizel

### Contexte

En tant qu'entreprise de statistiques, la compagnie Twitter Inc nous a demandé d'analyser les tweets d'internautes en temps réel afin de récupérer et d'illustrer le nombre de tweets par jour et par nationalité, ainsi que la taille des tweets en question. Cela leur permettra de mettre en place une infrastructure dynamique pour s'adapter à la charge selon l'heure de la journée.

Pour cela 3 métriques sont à mettre en place :

- l'origine de l'utilisateur
- la date de publication du tweet
- la taille - en nombre de caractères - du tweet

Ces informations seront ensuite illustrées à l'aide de graphiques en temps réel. 

### Techniques et Outils

- Afin de récupérer les tweets, Twitter fournit une [API](https://dev.twitter.com/rest/public).
- Pour lire les tweets en temps réel et effectuer des traitements sur ceux-ci, l'outil [Spark streaming](http://spark.apache.org/streaming/) sera utilisé.
- Pour la partie analyse de données et visualisation, le couple [ElasticSearch](https://www.elastic.co/products/elasticsearch) / [Kibana](https://www.elastic.co/products/kibana) convient parfaitement.
