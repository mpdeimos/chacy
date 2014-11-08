type(type) ::= <<
<type.modifiers; separator=" "> <type.kind; format="lower"> <type.name>
{
	<type.methods:method(); separator="\n\n">
}
>>