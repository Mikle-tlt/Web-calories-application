<!DOCTYPE html>
<html>
<head>
    <title>MIKLE_USER</title>
</head>
<body>
<h1>MIKLE_USER</h1>
<h3>Подробная информация о пользователе ${user.name}</h3>
<h4>Продукты пользователя</h4>
<#list products as product>
    <div>
        <p><b>${product.title}</b> ${product.price} руб. | <a href="/product/${product.id}">Подробнее...</a>
        </p> <#-- выводим информацию, обращаясь к листу -->
    </div>
    <#else>
    <h3>Товаров нет</h3>
</#list>
<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>
</body>
</html>