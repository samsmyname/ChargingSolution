package chargingScheduler;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class ScheduleFitness extends FitnessFunction{
	
	private final int m_targetAmount;
	
	public ScheduleFitness( int a_targetAmount )
    {
        if( a_targetAmount < 1 || a_targetAmount > 99 )
        {
            throw new IllegalArgumentException(
                "Amount must be between 1 and 99 cents." );
        }

        m_targetAmount = a_targetAmount;
    }


	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
