package main;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;

public class Principal {

	private static final int MAX_ALLOWED_EVOLUTIONS = 500;

	public static void printStatus(IChromosome cromosoma){
		System.out.println(
				FuncionAptitudVinos.getProporcionVino(
						cromosoma, 0 ) * 100 + "% de uva Cabernet-Sauvignon" );

		System.out.println(
				FuncionAptitudVinos.getProporcionVino(
						cromosoma, 1 ) * 100 + "% de uva Merlot" );

		System.out.println(
				FuncionAptitudVinos.getProporcionVino(
						cromosoma, 2 ) * 100 + "% de uva Malbec" );

		System.out.println(
				FuncionAptitudVinos.getProporcionVino(
						cromosoma, 3 ) * 100 + "% de uva Syrah" );

		System.out.println("--------------------------------");

	}

	public static void main(String[] args) throws Exception {
		
		Configuration conf = new DefaultConfiguration();

		FitnessFunction myFunc =
				new FuncionAptitudVinos();

		conf.setFitnessFunction( myFunc );

		Gene[] sampleGenes = new Gene[ 4 ]; // Armaremos nuestro vino utilizando 4 variedades de uva

		sampleGenes[0] = new DoubleGene(conf, 0, 1 );  // Cabernet Sauvignon
		sampleGenes[1] = new DoubleGene(conf, 0, 1 );  // Merlot
		sampleGenes[2] = new DoubleGene(conf, 0, 1 );  // Malbec
		sampleGenes[3] = new DoubleGene(conf, 0, 1 );  // Syrah

		Chromosome sampleChromosome = new Chromosome(conf, sampleGenes );

		conf.setSampleChromosome( sampleChromosome );

		conf.setPopulationSize( 500 );
		
		Genotype population = Genotype.randomInitialGenotype( conf );
		for( int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++ )
		{
			population.evolve();
			IChromosome partialSolution = population.getFittestChromosome();
			System.out.println( "Solución en iteracion nro " + i + " : " );
			printStatus(partialSolution);
		}
		IChromosome bestSolutionSoFar = population.getFittestChromosome();

		System.out.println( "Solución final: " );
		printStatus(bestSolutionSoFar);

	}

}
