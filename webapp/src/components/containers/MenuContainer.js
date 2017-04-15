import {connect} from 'react-redux';
import Menu from '../Menu';

function mapStateToProps(state, ownProps) {
    return {
        active: function (path) {
            switch (path) {
                case '/logout':
                    return '';
                case '/login':
                    return 'login';
                case '/':
                case '/future':
                    return 'future';
                case '/plan':
                    return 'plan';
                case '/past':
                    return 'past';
                case '/settings':
                    return 'settings';
                case '/admin':
                    return 'admin';
            }
        }(ownProps.location.pathname),
        isAdmin: state.app.auth.roles.indexOf('admin') > -1,
        isAuthenticated: state.app.auth.token !== null
    };
}

export default connect(mapStateToProps, null)(Menu);
