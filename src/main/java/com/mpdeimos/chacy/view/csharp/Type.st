#if(type.namespace)#
namespace #type.namespace#
{
	#ActualType(type)#
}
#else#
#ActualType(type)#
#endif#