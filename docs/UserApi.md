# UserApi

All URIs are relative to *http://localhost:8080/cafe-api/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**userIdPut**](UserApi.md#userIdPut) | **PUT** /user/{id} | Update user
[**userPost**](UserApi.md#userPost) | **POST** /user | Create new user
[**usersGet**](UserApi.md#usersGet) | **GET** /users | Retrieve all users

<a name="userIdPut"></a>
# **userIdPut**
> userIdPut(body, id)

Update user

Update user

### Example
```java
// Import classes:
//import vn.cmax.cafe.ApiClient;
//import vn.cmax.cafe.ApiException;
//import vn.cmax.cafe.Configuration;
//import vn.cmax.cafe.auth.*;
//import vn.cmax.cafe.api.UserApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


UserApi apiInstance = new UserApi();
UserRequest body = new UserRequest(); // UserRequest | 
Integer id = 56; // Integer | 
try {
    apiInstance.userIdPut(body, id);
} catch (ApiException e) {
    System.err.println("Exception when calling UserApi#userIdPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserRequest**](UserRequest.md)|  |
 **id** | **Integer**|  |

### Return type

null (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="userPost"></a>
# **userPost**
> userPost(body)

Create new user

Create new user

### Example
```java
// Import classes:
//import vn.cmax.cafe.ApiClient;
//import vn.cmax.cafe.ApiException;
//import vn.cmax.cafe.Configuration;
//import vn.cmax.cafe.auth.*;
//import vn.cmax.cafe.api.UserApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


UserApi apiInstance = new UserApi();
UserRequest body = new UserRequest(); // UserRequest | 
try {
    apiInstance.userPost(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UserApi#userPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserRequest**](UserRequest.md)|  |

### Return type

null (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="usersGet"></a>
# **usersGet**
> UserSearchResponse usersGet(role, page, pageSize)

Retrieve all users

Retrieve all users

### Example
```java
// Import classes:
//import vn.cmax.cafe.ApiClient;
//import vn.cmax.cafe.ApiException;
//import vn.cmax.cafe.Configuration;
//import vn.cmax.cafe.auth.*;
//import vn.cmax.cafe.api.UserApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


UserApi apiInstance = new UserApi();
Role role = new Role(); // Role | 
Integer page = 56; // Integer | 
Integer pageSize = 56; // Integer | 
try {
    UserSearchResponse result = apiInstance.usersGet(role, page, pageSize);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserApi#usersGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **role** | [**Role**](.md)|  | [optional]
 **page** | **Integer**|  | [optional] [enum: ]
 **pageSize** | **Integer**|  | [optional] [enum: ]

### Return type

[**UserSearchResponse**](UserSearchResponse.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

