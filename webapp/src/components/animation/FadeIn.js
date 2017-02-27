import React from 'react';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import styles from './FadeIn.css';

class FadeIn extends React.Component {
    render() {
        const children = React.Children.map(this.props.children, child => <div key={child}>{child}</div>);

        return (
            <div className={styles.component}>
                <ReactCSSTransitionGroup
                    transitionName="fadein"
                    transitionAppear={true}
                    transitionAppearTimeout={500}
                    transitionEnter={true}
                    transitionEnterTimeout={500}
                    transitionLeave={false}
                    transitionLeaveTimeout={0}>
                    {children}
                </ReactCSSTransitionGroup>
            </div>
        );
    }
}

FadeIn.propTypes = {
    children: React.PropTypes.any.isRequired,
};

export default FadeIn;
