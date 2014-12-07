'use strict';

angular.module('vino.ui').constant('LocalDictionary', {


    'app.name': 'Vino',
    'app.version': '1.0',
    'main.developer': 'Elian ORIOU',

    // MENUS ITEMS
    'menu.home': 'Accueil',
    'menu.domains': 'Chateaux',
    'menu.cellars': 'Caves',
    'menu.cellar': 'Contenu Cave',
    'menu.statistics': 'Statistiques',

    // COMMON
    'cellar': 'Cave',
    'barcode': 'Code-Barre',
    'localization': 'Localisation',
    'description': 'Description',
    'qty': 'Quantité',

    // ACTIONS
    'actions': 'Actions',
    'action.search': 'Rechercher',
    'action.view': 'Voir',
    'action.edit': 'Modifier',
    'action.create': 'Créer',
    'action.delete': 'Supprimer',
    'action.put': 'Ajouter',
    'action.pull': 'Retirer',
    'action.ok': 'Valider',
    'action.cancel': 'Annuler',
    'action.select.cellar': 'Selectionnez une Cave',
    'action.select.aoc': 'Selectionnez une AOC ... ou ',
    'action.displayAllDomains': 'Afficher tous',
    'action.add.bottle': 'Ajouter une bouteille dans la cave',

    // DIALOG
    'action.delete.domain.confirm': 'Confirmez-vous la suppression de ce château ?',
    'action.delete.cellar.confirm': 'Confirmez-vous la suppression de cette cave?',

    // NOTIFICATIONS
    'domain.create.success': 'Château crée avec succès',
    'domain.create.error': 'Erreur durant la création du château',
    'domain.update.success': 'Château mis à jour avec succès.',
    'domain.update.error': 'Erreur durant la mise à jour du château',
    'domain.delete.success': 'Château supprimé avec succès.',
    'domain.delete.error': 'Erreur durant la suppression du château',
    'cellar.create.success': 'Cave ajoutée avec succès',
    'cellar.create.error': 'Erreur durant la création de la cave',
    'cellar.edit.success': 'Cave modifiée avec succès',
    'cellar.edit.error': 'Erreur durant la modification de la cave',
    'cellar.delete.success': 'Cave supprimée avec succès',
    'cellar.delete.error': 'Erreur durant la suppresion de la cave',
    'cellar.in.success': 'Bouteille ajoutée à la cave',
    'cellar.in.error': 'Erreur durant l\'ajout de la bouteille',
    'cellar.out.success': 'Bouteille retirée de la cave',
    'cellar.out.error': 'Erreur durant le retrait de la bouteille',
    'cellar.record.update.success': 'Informations de la bouteille mises à jour avec succès',
    'cellar.record.update.error': 'Erreur durant la mise à jour des informations',

    'notification.GENERIC_ERROR': 'Une erreur est survenue...',
    'notification.CELLAR_NOT_EMPTY': 'La cave n\'est pas vide, la suppression n\'est pas possible.',
    'notification.UNSUPPORTED_MOVEMENT_TYPE': 'Le type du mouvement n\'est pas supporté',
    'notification.RECORD_NOT_FOUND': 'L\'entrée de cave n\'a pas été trouvée',
    'notification.INVALID_EAN_CODE': 'Le code bar n\'est pas valide',

    // CELLAR
    'cellar.name': 'Nom',
    'cellar.description': 'Description',
    'cellar.localization': 'Localisation',

    // ORIGINS (REGION + AOC)
    'region': 'Région Viticole',
    'aoc': 'AOC',

    // DOMAIN
    'domain': 'Château',
    'domains': 'Châteaux',
    'domain.name': 'Nom',
    'domain.sticker': 'Etiquette',
    'domain.identity': 'Identité',

    // BOTTLE
    'bottle.vintage': 'Millésime',
    'inCellar.bottles': 'Bouteilles en cave',

    // WINE
    'wine': 'Vin',
    'wine.comments': 'Commentaires',
    'wine.garde': 'Garde',
    'wine.temperatures': 'Conservation (°C)',
    'wine.tasting': 'Arômes',
    'wine.floor': 'Sol',
    'wine.grapes': 'Cépages',
    'wine.medals': 'Médailles',
    'wine.history': 'Historique'

});