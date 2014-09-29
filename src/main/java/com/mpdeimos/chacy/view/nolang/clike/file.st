file(type) ::= <<
<if(type.namespace)>
namespace <type.namespace>
{
	<type(type)>
}
<else>
<type(type)>
<endif>
>>