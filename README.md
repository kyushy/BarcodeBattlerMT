# BarcodeBattlerMT

- Ajout de créature, potion, équipement en scannant un code barre
- Faire un combat local contre un bot
- Faire un combat en bluetooth contre un humain
- Affichage de la liste des créatures et la liste des équippements

## Combat
- Les combat se déroulle en style Pierre feuille ciseaux (attaque, riposte, special)

## Réseau
- envoie de json qui contiennent les données
- 3 type de données envoyée
	- les informations sur le monstre
	- le type de l'attaque (envoyé par le premier joueur à choisir son action)
	- le résultat de l'attaque (determiné par le joueur le plus lent à jouer)
