package com.mpdeimos.chacy.transform;

import com.mpdeimos.chacy.config.Config;
import com.mpdeimos.chacy.model.Type;
import com.mpdeimos.chacy.model.deviant.TypeDeviant;

/**
 * Interface for transforming a type into a type of another programming
 * language.
 */
public interface Transformator
{
	/** Transforms the type with the given configuration. */
	public TypeDeviant[] transform(Type type, Config config);
}
