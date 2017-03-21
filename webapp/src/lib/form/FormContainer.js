import React from 'react';
import {connect} from 'react-redux';

class FormContainer extends React.Component {
    onSubmit(event) {
        this.props.onSubmit(this.props.state[this.context.formStateKey][this.props.name]);
        event.preventDefault();
    }

    render() {
        return React.cloneElement(this.props.children, {
            onSubmit: this.onSubmit.bind(this)
        });
    }

    getChildContext() {
        const self = this;
        return {
            update: function (...args) {
                self.props.update(self.props.name, ...args);
            },
        };
    }
}

FormContainer.propTypes = {
    children: React.PropTypes.element,
    /**
     * The form name, used to find state[formStateKey][name].
     */
    name: React.PropTypes.string.isRequired,
    /**
     * The method called when a FormFieldContainer's content is updated, provided by mapDispatchToProps as we have to
     * dispatch an event.
     */
    update: React.PropTypes.func,
    /**
     * Callback executed when the form is submitted. The first parameter contains the form field state.
     */
    onSubmit: React.PropTypes.func,
    /**
     * The form's state
     */
    state: React.PropTypes.object
};
FormContainer.childContextTypes = {
    /**
     * This method will be passed to children, via the childContext. It expects to have as 1st parameter the field's
     * name and as 2nd parameter the field's value.
     */
    update: React.PropTypes.func,
};
FormContainer.contextTypes = {
    /**
     * One of teh ancestors must provide a formStateKey, ie. the key in the global state under which are stored the form
     * states. It is used to find state[formStateKey].
     */
    formStateKey: React.PropTypes.string.isRequired
};

function mapStateToProps(state) {
    return {
        state
    };
}

function mapDispatchToProps(dispatch) {
    return {
        update: (form, name, value) => dispatch({
            type: FORM_UPDATE_VALUE,
            form,
            name,
            value
        })
    };
}

const ConnectedFormContainer = connect(mapStateToProps, mapDispatchToProps)(FormContainer);

const FORM_UPDATE_VALUE = 'FORM_UPDATE_VALUE';
const initialState = {};
function reduce(state = initialState, action) {
    function initialFormState(formName, state) {
        if (typeof state[formName] === 'undefined') {
            return {};
        }

        return state[formName];
    }

    switch (action.type) {
        case FORM_UPDATE_VALUE: {
            const newValues = Object.assign({}, initialFormState(action.form, state), {
                [action.name]: action.value
            });

            return Object.assign({}, state, {
                [action.form]: newValues
            });
        }

        default:
            return state;
    }
}

export {
    ConnectedFormContainer as FormContainer,
    reduce as reducer
};

