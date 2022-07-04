var table = null;
var tableAgg = null;
$.noConflict();
jQuery( document ).ready(function( $ ) {
    $.ajax({
        type: 'POST',
        url: 'ProductLoader',
        dataType: 'json',
        success: function (response) {
            if (response == "Server Error") {
                alert("Server or Data Error!");
            } else {
                populate_select(response)
                create_table( $ , response)
            }
        }
    });
    $('#minW').on('change keydown paste input', function(){ check_values(); });
    $('#maxW').on('change keydown paste input', function(){ check_values(); });
    $('#minP').on('change keydown paste input', function(){ check_values(); });
    $('#maxP').on('change keydown paste input', function(){ check_values(); });
    $('#btnApply').on('click', function() {
        compute_query();
    });
    $('#insertProductBtn').on('click', function() {
        insert_product();
        $("#newPname").val("");
        $("#newPcategory").val("");
        $("#newPprice").val("");
        $("#newPweight").val("");
        $("#newPdescription").val("");
        $("#newPtechnicalDetails").val("");
        $("#newPProductUrl").val("");
        $("#newPProductimage").val("");
    });

    $('#editProductBtn').on('click', function() {
        edit_product();
    });


    $("#weightAVG,#priceAVG").on('click', function () {
        var value = $(this).val();
        aggregate_function(value);
    });
    $("#tableAggBody").hide();
    $("#tablePrBody").show();
});

function create_table( $ , data) {
    if (tableAgg == null) {
        tableAgg = $('#tableAggregate').DataTable( {
            scrollX:true,
            scrollY: 500,
            scrollCollapse: true,
            "searching":false
        });
    }
    if (table == null) {
        table = $('#tableProducts').DataTable( {
            scrollX:true,
            scrollY: 500,
            scrollCollapse: true,
            "searching":false
        });
    }

    if (data.Products.length == 0) {
        table.clear().draw();
    } else {
        table.clear();
        for (var i = 0; i < data.Products.length; i++) {
            var d = data.Products[i]
            var dataRow = [
                d['Product Name'],
                d['Category'],
                "&euro; " + d['Selling Price'],
                d['Shipping Weight'].toString()+" kg",
                "<div id= \"btnProduct\">" +
                "<button type=\"button\" class=\"infoButton btn btn-primary\" value = "+ d['_id'] +" data-toggle=\"modal\" data-target=\"#infoProduct\"><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-info-circle\" viewBox=\"0 0 16 16\">\n" +
                "  <path d=\"M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z\"/>\n" +
                "  <path d=\"m8.93 6.588-2.29.287-.082.38.45.083c.294.07.352.176.288.469l-.738 3.468c-.194.897.105 1.319.808 1.319.545 0 1.178-.252 1.465-.598l.088-.416c-.2.176-.492.246-.686.246-.275 0-.375-.193-.304-.533L8.93 6.588zM9 4.5a1 1 0 1 1-2 0 1 1 0 0 1 2 0z\"/>\n" +
                "</svg></button>"
                + "<button type=\"button\" class=\"editButton btn btn-primary\" value = "+ d['_id'] +" data-toggle=\"modal\" data-target=\"#editProduct\"><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-pencil-square\" viewBox=\"0 0 16 16\">\n" +
                "  <path d=\"M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z\"/>\n" +
                "  <path fill-rule=\"evenodd\" d=\"M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z\"/>\n" +
                "</svg></button>"
                + "<button type=\"button\" class=\"deleteButton btn btn-primary\" value = "+ d['_id'] +"><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-x-circle\" viewBox=\"0 0 16 16\">\n" +
                "  <path d=\"M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z\"/>\n" +
                "  <path d=\"M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z\"/>\n" +
                "</svg></button>"
                + "</div>"

            ]
            table.row.add(dataRow).draw();
        }


        $('.editButton').on('click', function () {
            $("#editPName").val("");
            $("#editPcategory").val("");
            $("#editPprice").val("");
            $("#editPweight").val("");
            $("#editPdescription").val("");
            $("#editPtechnicalDetails").val("");
            $("#editPProductUrl").val("");
            $("#editPProductimage").val("");
            var value = $(this).val();
            $.ajax({
                type: 'POST',
                url: 'ProductQuery',
                dataType: 'json',
                data: {"flag":2, "idProduct" : value},
                success: function (response) {
                    if (response == "Server Error") {
                        alert("Obtaining information Failed, Server or Data Error!");
                    } else {
                        populate_edit_product(response);
                    }
                }
            });
        });


        $('.infoButton').on('click', function () {
            $('#infoPName').val("");
            $('#infoPDescription').val("");
            $('#infoPtechnicalDetails').val("");
            $('#infoPProductUrl').attr('href', "");
            $('#infoPImage').attr('src', "");
            var value = $(this).val();
            $.ajax({
                type: 'POST',
                url: 'ProductQuery',
                dataType: 'json',
                data: {"flag":2, "idProduct" : value},
                success: function (response) {
                    if (response == "Server Error") {
                        alert("Obtaining information Failed, Server or Data Error!");
                    } else {
                        populate_info_product(response);
                    }
                }
            });
        });


        $('.deleteButton').on('click', function () {
            var value = $(this).val();
            $.ajax({
                type: 'POST',
                url: 'ProductQuery',
                dataType: 'json',
                data: {"flag":3, "idProduct" : value},
                success: function (response) {
                    if (response == "Server Error") {
                        alert("Delete Failed, Server or Data Error!");
                    } else {
                        populate_select(response)
                        create_table( $ , response)
                    }
                }
            });
        });
    }

}

function compute_query() {

    var productName = $("#productName").val();
    var selectedVal = $("#categorySelect option:selected").val();
    var minPrice = parseFloat($("#minP").val());
    var maxPrice = parseFloat($("#maxP").val());
    var minWeight = parseFloat($("#minW").val());
    var maxWeight = parseFloat($("#maxW").val());

    if (productName === "") {
        productName = null;
    }
    if (minPrice === maxPrice === 0) {
        minPrice = null;
        maxPrice = null;
    } else if (minWeight === maxWeight === 0) {
        minWeight = null;
        maxWeight = null;
    }
    $.ajax({
        type: 'POST',
        url: 'ProductQuery',
        dataType: 'json',
        data: {"flag":0, "productName" : productName, "selectedValue": selectedVal, "minPrice": minPrice, "maxPrice": maxPrice, "minWeight": minWeight, "maxWeight": maxWeight},
        success: function (response) {
            if (response == "Server Error") {
                alert("Server Error!");
            } else {
                create_table( $ , response)
            }
        }
    });
}


function insert_product() {
    var productName = $("#newPname").val();
    var category = $("#newPcategory").val();
    var price = parseFloat($("#newPprice").val());
    var weight = parseFloat($("#newPweight").val());
    var aboutProd = $("#newPdescription").val();
    var techDetails = $("#newPtechnicalDetails").val();
    var productUrl = $("#newPProductUrl").val();
    var imageUrl = $("#newPProductimage").val();

    if (productName != "" && category != "" && price != ""
        && weight != "" && aboutProd != "" && techDetails != ""
        && productUrl != "") {
        $.ajax({
            type: 'POST',
            url: 'ProductQuery',
            dataType: 'json',
            data: {"flag":1, "productName" : productName, "category": category, "price": price, "weight": weight,
                "aboutProd": aboutProd, "techDet": techDetails, "productUrl":productUrl, "imageUrl":imageUrl
            },
            success: function (response) {
                if (response == "Server Error") {
                    alert("Insertion Failed, Server or Data Error!");
                } else {
                    alert("Insertion Successful!");
                    populate_select(response)
                    create_table( $ , response)
                }
            }
        });
    }
}

function check_values() {

    var minPrice = parseFloat($("#minP").val());
    var maxPrice = parseFloat($("#maxP").val());
    var minWeight = parseFloat($("#minW").val());
    var maxWeight = parseFloat($("#maxW").val());

    var checkPrices = true;
    var checkWeight = true;

    if (minPrice > maxPrice) {
        checkPrices = false;
    }
    if (minWeight > maxWeight) {
        checkWeight = false;
    }

    if (checkWeight || checkPrices) {
        $('#btnApply').prop("disabled", false);
    } else {
        $('#btnApply').prop("disabled", true);
    }

}


function populate_select(data) {
    categories = []

    for (var i = 0; i < data.Products.length; i++) {
        category = data.Products[i]['Category']
        if (!categories.includes(category)) {
            categories.push(category);
        }
    }
    var select = document.getElementById("categorySelect");
    $('#categorySelect').empty();

    var option = document.createElement('option');
    option.setAttribute('value', "null");
    option.appendChild(document.createTextNode("Default select"));
    select.appendChild(option);

    categories.sort();
    for (var i = 0; i < categories.length; i++) {
        var option = document.createElement('option');
        option.setAttribute('value', categories[i]);
        option.appendChild(document.createTextNode(categories[i]));
        select.appendChild(option);
    }
}

function populate_info_product(data) {
    var d = data.Product[0];
    $('#infoPName').val(d['Product Name']);
    $('#infoPDescription').val(d['About Product']);
    $('#infoPtechnicalDetails').val(d['Technical Details']);
    $('#infoPProductUrl').attr('href', d['Product Url']);
    $('#infoPImage').attr('src', d['Image']);

}

function populate_edit_product(data) {
    var d = data.Product[0];
    $("#editPName").val(d['Product Name']);
    $("#editPcategory").val(d['Category']);
    $("#editPprice").val(d['Selling Price']);
    $("#editPweight").val(d['Shipping Weight']);
    $("#editPdescription").val(d['About Product']);
    $("#editPtechnicalDetails").val(d['Technical Details']);
    $("#editPProductUrl").val(d['Product Url']);
    $("#editPProductimage").val(d['Image']);
    $("#objectIdEdit").val(d['_id']);

}

function edit_product() {

    var newName = $("#editPName").val();
    var newCategory = $("#editPcategory").val();
    var newPrice = $("#editPprice").val();
    var newWeight = $("#editPweight").val();
    var newDesc = $("#editPdescription").val();
    var newTech = $("#editPtechnicalDetails").val();
    var newUrl = $("#editPProductUrl").val();
    var newImage = $("#editPProductimage").val();
    var objectID = $("#objectIdEdit").val();
    $.ajax({
        type: 'POST',
        url: 'ProductQuery',
        dataType: 'json',
        data: {"flag": 4, "productID": objectID, "productName" : newName, "category": newCategory, "price": newPrice, "weight": newWeight,
            "aboutProd": newDesc, "techDet": newTech, "productUrl": newUrl, "imageUrl":newImage},
        success: function (response) {
            if (response == "Server Error") {
                alert("Update Failed, Server or Data Error!");
            } else {
                alert("Update Successful!");
                populate_select(response)
                create_table( $ , response)
            }
        }
    });
}


function aggregate_function(valBtn) {

    $.ajax({
        type: 'POST',
        url: 'ProductQuery',
        dataType: 'json',
        data: {"flag":5, "aggregateType" : valBtn},
        success: function (response) {
            if (response == "Type Error" || response == "Server Error") {
                alert("Aggregate Failed, Server or Type Error!");
            } else {
                crate_aggregate_table($, response, valBtn)
                $('#btnApply').prop("disabled", true);

                $('#resetAggregate').on('click', function () {
                    tableAgg.clear().draw();
                    $("#tableAggBody").hide();
                    $("#tablePrBody").show();
                    $('#btnApply').prop("disabled", false);
                });
            }
        }
    });
}

function crate_aggregate_table( $ , data, valBtn) {

    tableAgg.clear();

    for (var i = 0; i < data.Aggregates.length; i++) {
        var d = data.Aggregates[i]
        var dataRow;
        if (valBtn == 'weight') {
            dataRow = [
                d['_id'],
                d['Average'].toFixed(2) + ' kg'
            ]
        } else {
            dataRow = [
                d['_id'],
                "&euro; " + d['Average'].toFixed(2)
            ]
        }
        tableAgg.row.add(dataRow).draw();
    }

    $("#tableAggBody").show();
    $("#tablePrBody").hide();
}