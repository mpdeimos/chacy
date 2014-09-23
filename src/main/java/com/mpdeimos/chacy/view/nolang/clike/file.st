file(type) ::= <<
<if(type.namespace)>
namespace <type.namespace>
{
	<(type.kind)(type)>
}
<else>
<(type.kind)(type)>
<endif>
>>