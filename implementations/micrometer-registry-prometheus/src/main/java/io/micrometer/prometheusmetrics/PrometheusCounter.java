/*
 * Copyright 2017 VMware, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micrometer.prometheusmetrics;

import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.Nullable;
import io.micrometer.core.instrument.AbstractMeter;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.prometheus.metrics.core.exemplars.ExemplarSampler;
import io.prometheus.metrics.model.snapshots.Exemplar;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.DoubleAdder;

/**
 * {@link Counter} for Prometheus.
 *
 * @author Jon Schneider
 * @author Jonatan Ivanov
 */
public class PrometheusCounter extends AbstractMeter implements Counter {

    private final DoubleAdder count = new DoubleAdder();

    private final AtomicReference<Exemplar> exemplar = new AtomicReference<>();

    @Nullable
    private final ExemplarSampler exemplarSampler;

    PrometheusCounter(Meter.Id id) {
        this(id, null);
    }

    PrometheusCounter(Meter.Id id, @Nullable ExemplarSampler exemplarSampler) {
        super(id);
        this.exemplarSampler = exemplarSampler;
    }

    @Override
    public void increment(double amount) {
        if (amount > 0) {
            count.add(amount);
            if (exemplarSampler != null) {
                updateExemplar(amount, exemplarSampler);
            }
        }
    }

    @Override
    public double count() {
        return count.doubleValue();
    }

    @Nullable
    Exemplar exemplar() {
        return exemplar.get();
    }

    // Similar to exemplar.updateAndGet(...) but it does nothing if the next value is null
    private void updateExemplar(double amount, @NonNull ExemplarSampler exemplarSampler) {
        // Exemplar prev;
        // Exemplar next;
        // do {
        // prev = exemplar.get();
        // next = exemplarSampler.sample(amount, prev);
        // }
        // while (next != null && next != prev && !exemplar.compareAndSet(prev, next));
    }

}
