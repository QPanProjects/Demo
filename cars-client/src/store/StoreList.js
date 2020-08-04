import React, { Component } from 'react';
import { getAllStores, getAllUserCreatedStores } from '../util/APIUtils';
import Store from './Store';
import LoadingIndicator  from '../common/LoadingIndicator';
import { Button, Icon, notification } from 'antd';
import { STORE_LIST_SIZE } from '../constants';
import { withRouter } from 'react-router-dom';
import './StoreList.css';

class StoreList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            stores: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        };
        this.loadStoreList = this.loadStoreList.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
    }

    loadStoreList(page = 0, size = STORE_LIST_SIZE) {
        let promise;
        if(this.props.username) {
            if(this.props.type === 'USER_CREATEDD_STORES') {
                promise = getAllUserCreatedStores(this.props.username, page, size);
            }
        } else {
            promise = getAllStores(page, size);
        }

        if(!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
        .then(response => {
            const stores = this.state.stores.slice();

            this.setState({
                stores: stores.concat(response.content),
                page: response.page,
                size: response.size,
                totalElements: response.totalElements,
                totalPages: response.totalPages,
                last: response.last,
                isLoading: false
            })
        }).catch(error => {
            this.setState({
                isLoading: false
            })
        });  
        
    }

    componentDidMount() {
        this.loadStoreList();
    }

    componentDidUpdate(nextProps) {
        if(this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.setState({
                stores: [],
                page: 0,
                size: 10,
                totalElements: 0,
                totalPages: 0,
                last: true,
                isLoading: false
            });    
            this.loadStoreList();
        }
    }

    handleLoadMore() {
        this.loadStoreList(this.state.page + 1);
    }

    render() {
        const storeViews = [];
        this.state.stores.forEach((store, storeIndex) => {
            storeViews.push(<Store 
                key={store.id} 
                store={store} />)
        });

        return (
            <div className="stores-container">
                {storeViews}
                {
                    !this.state.isLoading && this.state.stores.length === 0 ? (
                        <div className="no-stores-found">
                            <span>No Stores Found.</span>
                        </div>    
                    ): null
                }  
                {
                    !this.state.isLoading && !this.state.last ? (
                        <div className="load-more-stores"> 
                            <Button type="dashed" onClick={this.handleLoadMore} disabled={this.state.isLoading}>
                                <Icon type="plus" /> Load more
                            </Button>
                        </div>): null
                }              
                {
                    this.state.isLoading ? 
                    <LoadingIndicator />: null
                }
            </div>
        );
    }
}

export default withRouter(StoreList);