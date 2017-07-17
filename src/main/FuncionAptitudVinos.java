package main;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class FuncionAptitudVinos extends FitnessFunction
{
    private static final long serialVersionUID = 1L;
    
    private static final double APTITUD_MINIMA = 0;
    private static final double APTITUD_MEDIA = 50;

    public double evaluate( IChromosome a_subject )
    {
    	double proporcionTotal = getProporcionTotal( a_subject );
    	
    	if (Math.abs(proporcionTotal - 1) > 0.01) { //La suma de las proporciones no deben ser distintas a 1
    		return APTITUD_MINIMA;
    	}
    	
    	double proporcionMaxima = getProporcionMaxima( a_subject );
    	
    	if(proporcionMaxima > 0.85){ //No queremos vinos varietales
    		return APTITUD_MINIMA;
    	}
    	
    	double aptitud = APTITUD_MEDIA;
    	
    	//Tengo Malbec y Cabernet-Sauvignon en mismas proporciones y supongo que esto lo hace mas apetecible
    	if(Math.abs(getProporcionVino(a_subject, 0) - getProporcionVino(a_subject, 2)) < 0.05){ 
    		aptitud += 200;
    	}
    	
    	//Imagino que no coseche mucha cantidad de Syrah, por lo que quiero no utilizar mas del 10% por mezcla
    	
    	if(getProporcionVino(a_subject, 3) < 0.1){ 
    		aptitud += 100;
    	}
    	
    	/* Por ultimo supongamos que debo agregar mucha cantidad de Malbec para contrarrestar cierta acidez del
    	 * Syrah (al menos el cuadruple de lo que utilizo de ese ultimo)
    	*/
    	
    	if(4*getProporcionVino(a_subject, 3) < getProporcionVino(a_subject, 2)){ 
    		aptitud += 100;
    	}
    	
    	return aptitud;
    }


    private double getProporcionMaxima(IChromosome a_subject) {
    	double proporcionMaxima = 0;

        int numberOfGenes = a_subject.size();
        for( int i = 0; i < numberOfGenes; i++ )
        {
            double proporcionActual = getProporcionVino( a_subject, i );
            if(proporcionActual > proporcionMaxima){
            	proporcionMaxima = proporcionActual;
            }
        }

        return proporcionMaxima;
	}

    public static double getProporcionVino( IChromosome solucionPotencial,
                                              int posicion )
    {
        Double proporcion =
          (Double) solucionPotencial.getGene(posicion).getAllele();

        return proporcion.doubleValue();
    }

    public static double getProporcionTotal( IChromosome a_potentialsolution )
    {
        double proporcionTotal = 0;

        int numberOfGenes = a_potentialsolution.size();
        for( int i = 0; i < numberOfGenes; i++ )
        {
            proporcionTotal += getProporcionVino( a_potentialsolution, i );
        }

        return proporcionTotal;
    }
}