# Using PMD

Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on its source code using any ruleset. Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false positive). Explain why you would not solve this issue.

## Answer

Le projet Java choisi est le projet d'Apache commons-cli.
Le ruleset choisi est le ruleset quickstart.xml fourni par PMD.

PMD trouve un problème :  
**“SimplifyBooleanReturns: Avoid unnecessary if..then..else statements when returning booleans”**

Cette erreur correspond au bout de code suivant : 
```java
if (!getMatchingLongOptions(t).isEmpty()) {
    // long or partial long options (--L, -L, --L=V, -L=V, --l, --l=V)
    return true;
}

if (getLongPrefix(token) != null && !token.startsWith("--")) {
    // -LV
    return true;
}
 
return false;
```

Les if sont inutiles car on peut les remplacer par le code suivant :
```java
return (!getMatchingLongOptions(t).isEmpty()) || (getLongPrefix(token) != null && !token.startsWith("--"));
```

Cependant, le code devient moins lisible et plus compliqué à comprendre et maintenir.


PMD trouve aussi un problème qui n’est sans doute pas utile à résoudre pour l’instant :  
**“UncommentedEmptyMethodBody:    Document empty method body”**  
C’est juste une méthode de test qui ne comporte rien dans son body. Les développeurs ont sans doute l’intention d’implémenter le test plus tard. 
