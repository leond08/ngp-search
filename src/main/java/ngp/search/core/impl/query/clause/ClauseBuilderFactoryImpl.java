package ngp.search.core.impl.query.clause;


import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import ngp.search.core.api.query.clause.ClauseBuilder;
import ngp.search.core.api.query.clause.ClauseBuilderFactory;

@Component(
		immediate = true, 
		service = ClauseBuilderFactory.class
)
public class ClauseBuilderFactoryImpl implements ClauseBuilderFactory {

	@Override
	public ClauseBuilder getClauseBuilder(String queryType) {
		
		// TODO Auto-generated method stub
		
		if (_clauseBuilders == null) {
			return null;
		}
		
		for (ClauseBuilder clauseBuilder : _clauseBuilders) {
			if (clauseBuilder.canBuild(queryType)) {
				_log.info(clauseBuilder);
				return clauseBuilder;
			}
		}

			_log.info("No clause builder found for " + queryType);

		
		return null;
	}
	
	/**
	 * Add any registered clause builders to the list
	 * 
	 * @param clauseBuilder
	 */
	protected void addClauseBuilder(ClauseBuilder clauseBuilder) {
		
		if (_clauseBuilders == null) {
			_clauseBuilders = new ArrayList<ClauseBuilder>();
		}
		_clauseBuilders.add(clauseBuilder);
	}
	
	/**
	 * Remove clause builder
	 * 
	 * @param clauseBuilder
	 */
	protected void removeClauseBuilder(ClauseBuilder clauseBuilder) {
		
		_clauseBuilders.remove(clauseBuilder);
	}
	
	@Reference(
		bind = "addClauseBuilder",
		cardinality = ReferenceCardinality.MULTIPLE,
		service = ClauseBuilder.class,
		unbind = "removeClauseBuilder"
	)
	
	private volatile List<ClauseBuilder> _clauseBuilders;
	private static final Log _log = 
		LogFactoryUtil.getLog(ClauseBuilderFactoryImpl.class);
 
}
