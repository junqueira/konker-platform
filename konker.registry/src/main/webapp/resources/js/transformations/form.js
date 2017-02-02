var controller = {
    addNewRow : function(trLast, callback) {
        trNew = trLast.clone();
        trLast.after(trNew);
        this.reindexRows();
        callback(trNew);
    },
    applyNewIndex : function(row, index, parentIndex) {

    	row.find('select, input, button, tr').each(function() {
            var input = $(this);
            var newId = 'undefined';
            if(input.attr('id') != undefined){
                if(input.attr('id').indexOf('.headers') != -1 && parentIndex != undefined){
                       newId = input.attr('id').substring(0, 6).replace(/\d+/gi, parentIndex)
                        + input.attr('id').substring(6, input.attr('id').length).replace(/\d+/gi, index -1);
                } else {
                    newId = input.attr('id').replace(/\d+/gi, index -1);
                }
                input.attr('id', newId);
            }

            if(input.attr('name') != undefined){
                var newName = input.attr('name').replace(/\d+/gi, index -1);
                if(input.attr('name').indexOf('headers') != -1  && parentIndex != undefined){

                    var firstIndex = $(this).attr('name').substring(0, 8).replace(/\d+/gi, parentIndex);
                    var secondIndex = $(this).attr('name').substring(8, $(this).attr('name').length)
                        .replace(/\d+/gi, index -1);

                    newName = firstIndex + secondIndex;
                }

                input.attr('name',newName);
            }

            if(input.attr('data-target') != undefined){
                input.attr('data-target', input.attr('data-target').replace(/\d+/gi, index-1));
            }

        });
    },
    removeRow : function(tableRow, rowCount, callback) {
        if (rowCount > 1) {
            tableRows = tableRow.parent();
            tableRow.remove();
            this.reindexRows();
        } else {
            $(tableRow).find('input').each(function(index, row){
                row.value = '';
                controller.reindexRows(tableRows, parentIndex, isHeader);
            });
        }
        if(callback != undefined){
            callback();
        }
    },
<<<<<<< HEAD
    reindexRows : function(tableRows, parentIndex, isHeader) {
        if(isHeader){
            tableRows.find('tr.header-line').each( function(index, item) {
                controller.applyNewIndex($(item), index+1, parentIndex);
            });
        } else {
            tableRows = $('.transformationSteps').find('tr.restheaders')
            tableRows.each( function(index, item) {
                index++;
                controller.applyNewIndex($(item).parent(), index, parentIndex);
            });
        }
=======
    reindexRows : function() {

        $('div.step').each(function(key, value) {
            controller.applyNewIndex($(value), key + 1);
            controller.reindexHeaders(value, key);
        });
>>>>>>> 8929e93824477724a950bedfda8c6e4286c4f508

    },
    reindexHeaders : function(step, parentIndex) {

        $(step).find('tr.header-line').each(function(key, value) {
            controller.applyNewIndex($(value), key+1, parentIndex);
        });

    },
    onRemoveHeader :function() {
        var row = $(this).closest('tr');
        controller.removeRow(
            row,
            $(this).closest('.restheaders').find('tr.header-line').length);

    },
    onRemoveStep :function() {
        var row = $(this).closest('div');
        controller.removeRow(
            row,
            $('div.step').length
        );
    },
    onAddHeader :function() {
        controller.addNewRow(
                $(this).parent().parent().find("tr:last"),
                function(item){
                    item.find('input[type=text]').each(function(input){
                        this.value = '';
                    });
                    item.find('button.remove-header').on('click', controller.onRemoveHeader);
                });
    }
}

$(document).ready(function() {
    controller.tableBody = $('tbody');
    
    $('.transformationSteps .btn-add').on('click', function() {
        controller.addNewRow(
            $('div.step:last'),
            function(item){
                item.find('input[type=text]').each(function(input){
                    this.value = '';
                });
                item.find('button.remove-step').each(function(index, item){
                    $(item).on('click', controller.onRemoveStep);
                });
                item.find('button.add-header').each(function(index, item){
                    $(item).on('click', controller.onAddHeader);
                });
                item.find('button.remove-header').on('click', controller.onRemoveHeader);
                // the new step must have just one header
                item.find('tr.restheaders tr.header-line:not(:first)').remove();
                controller.reindexRows();
            });
    });
<<<<<<< HEAD
    $('.add-header').on('click', function() {
        controller.addNewRow(
            $(this).parent().parent().find("tr:last"),
            $(this).parent().parent().parent().find('tr.header-line').length,
            $(this).parent().parent().attr('id').split('-')[1],
            function(item){
                item.find('input[type=text]').each(function(input){
                    this.value = '';
                });
                item.find('button.remove-header').on('click', function(){
                    var row = $(this).closest('tr');
                    controller.removeRow(row, $(this).parent().parent().parent().find('tr.header-line').length,
                    true);
                });
            });
    });
    $('.remove-step').on('click', function() {
        var row = $(this).closest('div');
        controller.removeRow(
            row,
            $(this).parent().parent().parent().parent().parent().parent().parent().parent().find('tr.restparams').length,
            undefined,
            false,
            function(result){
                row.find('tr.header-line').each(function(index, headerline){
                    controller.removeRow(
                        headerline,
                        row.find('tr.header-line').length,
                        $(headerline).parent().parent().parent().parent().parent().attr('id').split('-')[1],
                        true);
                    });
            });
    });
     $('.remove-header').on('click', function() {
        var row = $(this).closest('tr');
        controller.removeRow(
            row,
            $(this).parent().parent().parent().find('tr.header-line').length,
            $(this).parent().parent().parent().parent().parent().parent().parent().parent().parent().parent().attr('id').split('-')[1],
            true);
     });

=======
    $('.add-header').on('click', controller.onAddHeader);
    $('.remove-step').on('click', controller.onRemoveStep);
    $('.remove-header').on('click', controller.onRemoveHeader);
>>>>>>> 8929e93824477724a950bedfda8c6e4286c4f508

    $('#btnYes').click(function() {
    	$('#removeItemModal').modal('hide');
    	$("input[type=hidden][name=_method]").val('delete');
    	$('form').submit();
    });

});