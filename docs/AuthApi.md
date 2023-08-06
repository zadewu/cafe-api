# AuthApi

All URIs are relative to *http://localhost:8080/cafe-api/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**authLoginPost**](AuthApi.md#authLoginPost) | **POST** /auth/login | Process login with username and password
[**authLogoutPost**](AuthApi.md#authLogoutPost) | **POST** /auth/logout | Logout with accessToken
[**authRefreshPost**](AuthApi.md#authRefreshPost) | **POST** /auth/refresh | Grant new access token

<a name="authLoginPost"></a>
# **authLoginPost**
> AuthenticationResponse authLoginPost(body)

Process login with username and password

### Example
```java
// Import classes:
//import vn.cmax.cafe.ApiException;
//import vn.cmax.cafe.api.AuthApi;


AuthApi apiInstance = new AuthApi();
AuthenticationRequest body = new AuthenticationRequest(); // AuthenticationRequest | 
try {
    AuthenticationResponse result = apiInstance.authLoginPost(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuthApi#authLoginPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AuthenticationRequest**](AuthenticationRequest.md)|  |

### Return type

[**AuthenticationResponse**](AuthenticationResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="authLogoutPost"></a>
# **authLogoutPost**
> authLogoutPost()

Logout with accessToken

Logout with accessToken

### Example
```java
// Import classes:
//import vn.cmax.cafe.ApiClient;
//import vn.cmax.cafe.ApiException;
//import vn.cmax.cafe.Configuration;
//import vn.cmax.cafe.auth.*;
//import vn.cmax.cafe.api.AuthApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


AuthApi apiInstance = new AuthApi();
try {
    apiInstance.authLogoutPost();
} catch (ApiException e) {
    System.err.println("Exception when calling AuthApi#authLogoutPost");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="authRefreshPost"></a>
# **authRefreshPost**
> RefreshTokenResponse authRefreshPost()

Grant new access token

Grant another accessToken

### Example
```java
// Import classes:
//import vn.cmax.cafe.ApiException;
//import vn.cmax.cafe.api.AuthApi;


AuthApi apiInstance = new AuthApi();
try {
    RefreshTokenResponse result = apiInstance.authRefreshPost();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuthApi#authRefreshPost");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**RefreshTokenResponse**](RefreshTokenResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

