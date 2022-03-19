var recordsApi = Vue.resource('/sort{/id}')

Vue.component('sort-form', {
    props: ['records'],
    data: function (){
        return{
            nums: ''
        }
    },
    template:
        '<b-container align="right">'+
            '<b-input-group class="mt-3">'+
                '<b-form-input type="text" placeholder="Введите элементы для сортировки через пробел" v-model="nums"></b-form-input>'+
                '<b-button variant="outline-primary" type="button" value="Save" @click="save">Сортировка</b-button>'+
            '</b-input-group>'+
        '</b-container>',
    methods: {
        save: function () {
            var record = {nums: this.nums};

            recordsApi.save({}, record).then(result =>
                result.json().then(data => {
                    this.records.push(data)
                    this.nums = ''
                })
            )
        }
    },
});

Vue.component('record-row',{
    props: ['record'],
    template: '<b-container>({{ record.id }}) {{ record.nums }}</b-container>',
});

Vue.component('records-list',{
    props: ['records'],
    template:
        '<b-container>'+
            '<b-container align="left" class="mt-5"><h5>Bubble Sort</h5></b-container>'+
            '<sort-form class="mt-1" :records="records"/>'+
            '<b-container align="left" class="mt-3"><h5>Предыдущие сортировки</h5></b-container>'+
            '<record-row class="mt-1" v-for="record in records" :key="record.id" :record="record"/>'+
        '</b-container>',
    created: function() {
      recordsApi.get().then(result =>
          result.json().then(data =>
              data.forEach(record => this.records.push(record))
          )
      )
    },
});

var app = new Vue({
    el: '#app',
    template: '<records-list :records="records" />',
    data: {
        records: [

        ],
    },
});