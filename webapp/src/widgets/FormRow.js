import React from 'react';
import styles from './FormRow.css';

class FormRow extends React.Component {
    render() {
        return (
            <div className={styles.component + ' ' + this.props.className}>
                {this.props.children}
            </div>
        );
    }
}

FormRow.propTypes = {
    children: React.PropTypes.node.isRequired,
    className: React.PropTypes.string,
};

export default FormRow;
