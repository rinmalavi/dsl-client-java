package com.dslplatform.client;

import com.dslplatform.patterns.History;
import com.dslplatform.patterns.ServiceLocator;
import com.dslplatform.patterns.Snapshot;
import com.dslplatform.test.simple.SimpleRoot;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class HistoryTest {

	@Test
	public void getHistory() throws Exception {
		ServiceLocator serviceLocator = Bootstrap.init(getClass().getResourceAsStream("/test-project.props"));
		SimpleRoot sr = new SimpleRoot();
		sr.create();
		sr.setI(5).update();
		sr.setI(7).update();
		sr.setI(11).update();

		List<History<SimpleRoot>> historyList = serviceLocator.resolve(ReportingProxy.class).getHistory(SimpleRoot.class, Arrays.asList(sr.getURI())).get();

		final List<Snapshot<SimpleRoot>> snapshots = historyList.get(0).getSnapshots();
		assertTrue(snapshots.size() == 4);
		final Snapshot<SimpleRoot> simpleRootSnapshot = snapshots.get(1);
		final String action = simpleRootSnapshot.getAction();
		assertTrue(action.equals("UPDATE"));
		final int i = simpleRootSnapshot.getValue().getI();
		assertTrue(i == 5);
		assertTrue(historyList.get(0).getSnapshots().size() == 4);
	}

}
