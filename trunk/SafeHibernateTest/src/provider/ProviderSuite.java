package provider;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={TestDataTransformer.class, TestAESProvider.class, TestDESProvider.class, TestRSAProvider.class, TestSignatureProvider.class})
public class ProviderSuite {

	
	
}
