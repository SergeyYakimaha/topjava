$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
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
});

function clearFilter() {
    $('#filter').find("input").val("");
    updateTable();
}

function filter_params() {
    $.get(
        "ajax/meals/filter",
        {
            startDate: $("#startDate").val(),
            endDate: $("#endDate").val(),
            startTime: $("#startTime").val(),
            endTime: $("#endTime").val()
        }
        ).done(function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}