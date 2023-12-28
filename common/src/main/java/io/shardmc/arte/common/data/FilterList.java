package io.shardmc.arte.common.data;

import java.util.Set;

public record FilterList(Set<String> elements, boolean whitelist) { }
