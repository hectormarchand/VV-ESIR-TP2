# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

## Answer

TCC and LCC definitions :  
- NP (maximum number of possible connections) = N * (N − 1) / 2 where N is the number of methods
- NDC = number of direct connections (number of edges in the connection graph)
- NID = number of indirect connections
- Tight class cohesion TCC = NDC / NP
- Loose class cohesion LCC = (NDC + NIC) / NP  

TCC is in the range 0..1, LCC is in the range 0..1. **Also, TCC <= LCC**.  
The higher TCC and LCC, the more cohesive the class is.  
LCC tells the overall connectedness. It depends on the number of methods and how they group together.
TCC tells the "connection density".  
TCC et LCC donnent la même valeur lorsque le nombre indirect de connexions est égale à zéro ou lorsque toutes les connexions entre les fonctions sont directes.

Exemple de Classe :
```java
public class Division {
 
    private int numerateur;
    private int denominateur;
 
    Division(int numerateur, int denominateur){
        this.numerateur = numerateur;
        this.denominateur = denominateur;
    }
 
    public int getNumerateur(){
        return numerateur;
    }
 
    public int getDenominateur(){
        return denominateur:
    }
 
    public void setNumerateur(int num){
        numerateur = num;
    }
 
    public void setDenominateur(int den){
        denominateur = den;
    }
 
    public int divisionEntiere(){
        assert(numerateur%denominateur) : "Division non entiere";
        return numerateur/denominateur;    
    }
}

```