// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );

    $(":checkbox").click(function() {
        update($(this).attr("id"), this.checked);
    });

});

function update(id, state) {
    $.ajax({
        type: "POST",
        url: context.ajaxUrl + id + "/state",
        data: {state: state}
    }).done(function (data) {
        updateTable_(data);
        successNoty((state) ? "Запись активирована" : "Запись деактивирована");
    });
}