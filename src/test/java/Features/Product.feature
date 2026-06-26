Feature: Tester la Gestion produit Module ProductPage

  Background:  Login avec les identifiants valides
    Given je suis sur la page Login
    When je saisi l'username "standard_user"
    And je saisi le mot de passe "secret_sauce"
    And je click sur le bouton Login
    Then redirection vers la page Home

# tester le bouton Addtocart de la page d'acceuil
  Scenario: Ajouter un produit de la page dacceuil au panier avec succés
    Given je suis sur la page dacceuil
    When je selectionne un produit
    And je click sur le bouton AddToCart
    And je click sur le panier
    Then produit ajouté au panier avec succés depuis la page d'acceuil

    # tester le bouton Addtocart de la page produit
  Scenario: Ajouter un produit de la page produit au panier avec succés
    Given je suis sur la page dacceuil
    When je click sur le produit
    And je click sur le bouton AddToCart du produit
    And je click sur le panier du produit
    Then produit ajouté au panier avec succés depuis la page produit



  #Persistance panier (rafraichissement de la page)
  Scenario: Vérifier la persistance du panier après rafraîchissement de la page
    Given je suis sur la page dacceuil
    When je click sur le bouton AddToCart
    And je rafraîchis la page du navigateur
    Then le compteur du panier doit toujours afficher 1



#tester la notification
  Scenario: Tester la notification lors de l ajout produit
    Given je suis sur la page dacceuil
    When je selectionne un produit
    And je click sur le bouton AddToCart
    Then notification dajout saffiche sur le panier

#tester l'incrementation compteur panier
  Scenario:  incrementation Compteur panier lors de l ajout produit
    Given je suis sur la page dacceuil
    When je click sur le bouton AddToCart
    And je click sur le bouton AddToCart DeuxiemeProduit
    Then incremetation panier reussi compteur panier affiche le chiffre deux

#tester le bouton Remove

  Scenario: tester le bouton remove du produit
    Given je suis sur la page dacceuil
    When je click sur le bouton AddToCart
    And je click sur Remove
    And je click sur le panier
    Then produit supprimé

# tester la decrementation apres suppression produit (panier vide)
  Scenario: tester la decrementation compteur panier lors de la suppression produit
    Given je suis sur la page dacceuil
    When je click sur le bouton AddToCart
    And je click sur Remove
    And je click sur le panier
    Then produit supprimé et le compteur  se decremente


    # tester la decrementation apres suppression produit (panier pas vide)
  Scenario: tester la decrementation compteur panier lors de la suppression produit
    Given je suis sur la page dacceuil
    When je click sur le bouton AddToCart
    And je click sur le bouton AddToCart DeuxiemeProduit
    And je click sur Remove premier produit ajouté
    And je click sur le panier
    Then premier produit supprimé et le compteur  se decremente

 # tester le filtre Z TO A
  Scenario: tester le tri des produits (Z to A)
    Given je suis sur la page dacceuil
    When je note l'ordre initial de la liste des produits dans le site
    And je click sur filtreicon je choisi ZTOA
    Then produits filtrés selon Name Z to A


    #tester l'image de l'icone Panier (vs image reference )
  Scenario: verifier l'icone panier (compatibilité et correspondance a une reference)
    Given je suis sur la page dacceuil
    When je click sur le bouton AddToCart
    Then le badge doit apparaitre au panier et correspendre visuellement a l'image ref



  #regression #functional
  Scenario: Vérifier la cohérence des informations sur la page de détails d'un produit
    Given je suis sur la page dacceuil
    When je note le nom, la description et le prix du premier produit
    And je click sur le nom du premier produit
    Then les informations affichées sur la page de détails doivent être identiques



  #navigation #ui
  Scenario: Retourner à la liste des produits depuis la page de détails
    Given je suis sur la page dacceuil
    When je click sur le nom du premier produit
    And je clique sur le bouton Back to Products
    Then je suis réorienté vers la page d'accueil des produits


