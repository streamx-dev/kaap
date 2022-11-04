package com.datastax.oss.pulsaroperator.crds;

import com.datastax.oss.pulsaroperator.crds.validation.ValidableSpec;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseComponentSpec<T> extends ValidableSpec<T> implements WithDefaults {

    protected String image;
    private String imagePullPolicy;
    protected Map<String, String> nodeSelectors;

    @Override
    public void applyDefaults(GlobalSpec globalSpec) {
        if (image == null) {
            image = globalSpec.getImage();
        }
        if (nodeSelectors == null) {
            nodeSelectors = globalSpec.getNodeSelectors();
        }
        if (imagePullPolicy == null) {
            imagePullPolicy = globalSpec.getImagePullPolicy();
        }
    }
}