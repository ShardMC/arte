package io.shardmc.arte.common.config.data;

import java.util.Set;

public record FilterList(Set<String> elements, boolean whitelist) { }
