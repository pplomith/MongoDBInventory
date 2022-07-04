<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Amazon Products</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js" type="text/javascript"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css">
    <link rel="stylesheet" href="css/index.css">

    <!--Import jQuery before export.js-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>

    <!--Data Table-->
    <script type="text/javascript"  src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript"  src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>
</head>
<body>

<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" href="index.html">Amazon Products</a>

</nav>


<div id="container" class="card mb-4">
    <div class="card-body" id="cardContainer">
    <div id="filterForm" class="card mb-4">
        <div class="card-header">Filters</div>
        <div class="card-body">
        <form>
            <form data-bitwarden-watching="1">
                <!-- Product Name input -->
                <div class="form-outline mb-4">
                    <label class="form-label" for="productName">Product Name</label>
                    <input type="text" id="productName" class="form-control">
                </div>
                <label class="form-label" for="categorySelect">Category</label>
                <select class="form-control" id="categorySelect">
                    <option value="null">Default select</option>
                </select>
                <!-- 2 column grid layout with text inputs for the first and last names -->
                <div class="row mb-4">
                    <div class="col">
                        <div class="form-outline">
                            <label class="form-label" for="minP">Min Price</label>
                            <input type="number" id="minP" class="form-control"  min="0" value="0" step="0.01">
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-outline">
                            <label class="form-label" for="maxP">Max Price</label>
                            <input type="number" id="maxP" class="form-control" min="0" value="0" step="0.01">
                        </div>
                    </div>
                </div>
                <div class="row mb-4">
                    <div class="col">
                        <div class="form-outline">
                            <label class="form-label" for="minW">Min Weight</label>
                            <input type="number" id="minW" class="form-control"  min="0" value="0" step="0.01">
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-outline">
                            <label class="form-label" for="maxW">Max Weight</label>
                            <input type="number" id="maxW" class="form-control"  min="0" value="0" step="0.01">
                        </div>
                    </div>
                </div>

                <!-- Submit button -->
                <div id="buttonsMain">
                <button type="button" class="btn btn-primary btn-block mb-4" id="btnApply">Apply</button>
                </div>
                <div id="buttonsBody">
                <button type="button" class="btn btn-primary btn-block mb-4" data-toggle="modal" data-target="#insertProduct">
                    Insert Product
                </button>
                </div>
                <div id="buttonsAggregate">
                    <label class="form-label" for="weightAVG">Average Weight for each category.</label>
                    <button type="button" id="weightAVG" class="btn btn-primary btn-block mb-4" value="weight">
                        Aggregate Weight
                    </button>
                    <label class="form-label" for="priceAVG">Average Price for each category.</label>
                    <button type="button" id="priceAVG" class="btn btn-primary btn-block mb-4" value="price">
                        Aggregate Price
                    </button>
                </div>
                <div>
                    <button type="button" id="resetAggregate" class="btn btn-primary btn-block mb-4">
                        Reset Aggregate
                    </button>
                </div>
            </form>
        </form>
        </div>
    </div>

    <div class="card mb-4" id="tableContainer">
        <div class="card-header">Products</div>
        <div class="card-body" id="tablePrBody">
        <table id="tableProducts" class="table table-striped table-bordered" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th class="th-sm">Product Name
                </th>
                <th class="th-sm">Category
                </th>
                <th class="th-sm">Selling Price
                </th>
                <th class="th-sm">Shipping Weight
                </th>
                <th class="th-sm">
                </th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
        </div>
        <div class="card-body" id="tableAggBody">
            <table id="tableAggregate" class="table table-striped table-bordered" cellspacing="0" width="100%" >
                <thead>
                <tr>
                    <th class="th-sm">Category
                    </th>
                    <th class="th-sm">Average Value
                    </th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>

    </div>
    </div>
</div>
</div>

<div class="modal fade" id="insertProduct" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
                <button type="button" class="btn btn-primary close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-outline mb-4">
                    <label class="form-label" for="productName">Product Name</label>
                    <input type="text" id="newPname" class="form-control" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPcategory">Category</label>
                    <input type="text" id="newPcategory" class="form-control" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPprice">Price</label>
                    <input type="number" id="newPprice" class="form-control" step="0.01" min="0" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPweight">Weight</label>
                    <input type="number" id="newPweight" class="form-control"step="0.01" min="0" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPdescription">About Product</label>
                    <input type="text" id="newPdescription" class="form-control" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPtechnicalDetails">Technical Details</label>
                    <input type="text" id="newPtechnicalDetails" class="form-control" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPProductUrl">Product URL</label>
                    <input type="text" id="newPProductUrl" class="form-control" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPProductimage">Product Image URL</label>
                    <input type="text" id="newPProductimage" class="form-control">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="insertProductBtn" data-dismiss="modal">Insert</button>
            </div>
        </div>
    </div>
</div>



<div class="modal fade" id="editProduct" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editProductModal">Modal title</h5>
                <button type="button" class="btn btn-primary close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-outline mb-4">
                    <label class="form-label" for="productName">Product Name</label>
                    <input type="text" id="editPName" class="form-control" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPcategory">Category</label>
                    <input type="text" id="editPcategory" class="form-control" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPprice">Price</label>
                    <input type="number" id="editPprice" class="form-control" step="0.01" min="0" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPweight">Weight</label>
                    <input type="number" id="editPweight" class="form-control"step="0.01" min="0" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPdescription">About Product</label>
                    <input type="text" id="editPdescription" class="form-control" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPtechnicalDetails">Technical Details</label>
                    <input type="text" id="editPtechnicalDetails" class="form-control" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPProductUrl">Product URL</label>
                    <input type="text" id="editPProductUrl" class="form-control" required>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="newPProductimage">Product Image URL</label>
                    <input type="text" id="editPProductimage" class="form-control">
                </div>
                <input type="hidden" id="objectIdEdit" name="objectIdEdit" value="">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="editProductBtn" data-dismiss="modal">Save</button>
            </div>
        </div>
    </div>
</div>




<div class="modal fade" id="infoProduct" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="infoProductModal">Product Information</h5>
                <button type="button" class="btn btn-primary close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-outline mb-4">
                    <label class="form-label" for="infoPName">Product Name</label>
                    <input type="text" id="infoPName" class="form-control" readonly>
                </div>
                <div class="form-outline mb-4">
                    <img id="infoPImage" src="" alt="" width="200" height="200">
                </div>
                <div class="form-outline mb-4">
                    <a id="infoPProductUrl" href="url">Amazon URL</a>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="infoPDescription">About Product</label>
                    <textarea readonly id="infoPDescription" class="form-control"></textarea>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="infoPtechnicalDetails">Technical Details</label>
                    <textarea readonly id="infoPtechnicalDetails" class="form-control"></textarea>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>



</body>

<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/jquery.dataTables.js" type="text/javascript"></script>

<script src = "js/bundle.js">

</script>

</html>
