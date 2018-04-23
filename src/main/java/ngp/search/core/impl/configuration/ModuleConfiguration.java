package ngp.search.core.impl.configuration;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
	id = "ngp.search.core.configuration.NgpSearch",
	localization = "content/Language",
	name = "NGP Search Core"
)
public interface ModuleConfiguration {
	
	@Meta.AD(
			deflt = "10", 
			description = "page-size-desc",
			name = "page-size-name",
			required = false
	)
	public int pageSize();
	
	@Meta.AD(
			deflt = "/viewasset", 
		    name = "asset-publisher-page-name",
		    description = "asset-publisher-page-desc",
			required = false
	)
	public String assetPublisherPage();

	@Meta.AD(
		deflt = "Query configuration as JSON string",
		description = "searchfield-configuration-desc",
		name = "searchfield-configuration-name",
		required = false
	)
	public String searchFieldConfiguration();
	
}
