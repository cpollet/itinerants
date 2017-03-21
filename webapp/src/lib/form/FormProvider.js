import React from 'react';

class FormProvider extends React.Component {
    render() {
        return this.props.children;
    }

    getChildContext() {
        return {
            formStateKey: this.props.stateKey
        };
    }
}

FormProvider.propTypes = {
    children: React.PropTypes.element,
    /**
     * The key under which the form states are stored in the global state.
     */
    stateKey: React.PropTypes.string.isRequired
};
FormProvider.childContextTypes = {
    /**
     * The key under which the form states are stored in the global state. This is copied from prop.stateKey
     */
    formStateKey: React.PropTypes.string
};
export default FormProvider;
