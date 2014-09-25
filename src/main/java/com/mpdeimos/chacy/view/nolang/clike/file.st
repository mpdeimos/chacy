file(type) ::= <<
<if(type.namespace)>
namespace <type.namespace>
{
	<(EType.(type.kind))(type)>
}
<else>
<(EType.(type.kind))(type)>
<endif>
>>