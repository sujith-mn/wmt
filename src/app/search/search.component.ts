import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  NgForm,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { __values } from 'tslib';
import { DataStorageService } from '../shared/services/data-storage.service';
import { Demand } from '../shared/model/demand';
import { assign } from '../shared/model/assign';
import { DatePipe } from '@angular/common';

export interface demandData {
  id: string;
  manager: string;
  created: string;
  endDate: string;
  ageing: string;
  priority: string;
  skill: string;
  status: string;
}
@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  [x: string]: any;
  Variable: any;
  ageingValue: any;
  demands: demandData[];
  editForm: FormGroup;
  detailForm: FormGroup;
  private deleteId: any;
  closeResult: string;
  displayedColumns: string[] = [
    'id',
    'manager',
    'created',
    'ageing',
    'skill',
    'status',
    'actions',
  ];
  statusVal: string[] = ['open', 'complete', 'pending', 'InProgress'];
  skillVal: string[] = ['Java', 'Angular', 'Spring framework', 'React'];
  dataSource: MatTableDataSource<demandData>;
  pipe: DatePipe = new DatePipe('en-US');

  public filterForm = new FormGroup({
    fromDatePicker: new FormControl(''),
    toDate: new FormControl(),
  });

  get fromDatePicker() {
    return this.filterForm.get('fromDatePicker').value;
  }
  get toDate() {
    return this.filterForm.get('toDate').value;
  }

  editedDemandValues: any;
  private ageingCal: any;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private httpClient: HttpClient,
    private modalService: NgbModal, //Add parameter of type NgbModal
    private fb: FormBuilder,
    private dataStorageService: DataStorageService,
    private router: Router
  ) {
    this.pipe = new DatePipe('en');
  }

  newDemand: FormGroup = this.fb.group({
    manager: [null, [Validators.required]],
    created: [null, [Validators.required]],
    endDate: [],
    ageing: [],
    priority: [null, [Validators.required]],
    skill: [null, [Validators.required]],
    status: [null, [Validators.required]],
  });
  ngOnInit(): void {
    this.newDemand.get('created')?.valueChanges.subscribe((value) => {
      console.log(value);
      var a: any = new Date(value);
      var today = new Date();
      var year = today.toLocaleString('default', { year: 'numeric' });
      var month = today.toLocaleString('default', { month: '2-digit' });
      var day = today.toLocaleString('default', { day: '2-digit' });

      const formattedDate: any = year + '-' + month + '-' + day;
      const dt = new Date(formattedDate);

      const _MS_PER_DAY = 1000 * 60 * 60 * 24;
      const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
      const utc2 = Date.UTC(dt.getFullYear(), dt.getMonth(), dt.getDate());
      console.log(Math.floor((utc2 - utc1) / _MS_PER_DAY).toString());
      this.newDemand.patchValue({
        ageing: Math.floor((utc2 - utc1) / _MS_PER_DAY).toString(),
      });

      return value;
    });
    this.dataStorageService.refreshneeds.subscribe(() => {
      this.getDemands();
    });
    this.getDemands();

    this.detailForm = this.fb.group({
      id: [''],
      manager: [''],
      created: [''],
      // endDate:[''],
      ageing: [''],
      priority: [''],
      skill: [''],
      status: [''],
    });
    this.editForm = this.fb.group({
      id: [''],
      manager: [null, Validators.required],
      created: [null, Validators.required],
      // endDate:[null, Validators.required],
      ageing: [null, Validators.required],
      priority: [null, Validators.required],
      skill: [null, Validators.required],
      status: [null, Validators.required],
    });
  }
  applyFilterDate() {
    // this.getFilterDate();
    this.dataSource.filter = '' + Math.random();
  }

  getDemands() {
    return this.dataStorageService.getAllDemands().subscribe(
      (response) => {
        // console.log(result)
        this.demands = response;

        this.demands.forEach((value) => {
          var a: any = new Date(value.created);
          var today = new Date();
          var year = today.toLocaleString('default', { year: 'numeric' });
          var month = today.toLocaleString('default', { month: '2-digit' });
          var day = today.toLocaleString('default', { day: '2-digit' });

          const formattedDate: any = year + '-' + month + '-' + day;
          const dt = new Date(formattedDate);

          const _MS_PER_DAY = 1000 * 60 * 60 * 24;
          const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
          const utc2 = Date.UTC(dt.getFullYear(), dt.getMonth(), dt.getDate());
          // console.log(Math.floor((utc2 - utc1) / _MS_PER_DAY).toString());
          value.ageing = Math.floor((utc2 - utc1) / _MS_PER_DAY).toString();
          return value;
        });

        this.dataSource = new MatTableDataSource<demandData>(this.demands);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;

        this.dataSource.filterPredicate = (data, filter) => {
          if (this.fromDatePicker && this.toDate) {
            console.log(this.fromDatePicker);
            console.log(this.toDate);
            let mySimpleFormatFromDate = this.pipe.transform(
              this.fromDatePicker,
              'yyyy-MM-dd'
            );
            let mySimpleFormatToDate = this.pipe.transform(
              this.toDate,
              'yyyy-MM-dd'
            );

            return (
              data.created >= mySimpleFormatFromDate &&
              data.created <= mySimpleFormatToDate
            );
          }
          return true;
        };
      },
      (errorResponse) => {
        console.log(errorResponse);
      },
      () => {
        console.log('complete');
      }
    );
  }

  getAgeing(_item: any) {
    var a: any = new Date(_item.created);
    var today = new Date();
    var year = today.toLocaleString('default', { year: 'numeric' });
    var month = today.toLocaleString('default', { month: '2-digit' });
    var day = today.toLocaleString('default', { day: '2-digit' });

    const formattedDate: any = year + '-' + month + '-' + day;
    const dt = new Date(formattedDate);

    const _MS_PER_DAY = 1000 * 60 * 60 * 24;
    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
    const utc2 = Date.UTC(dt.getFullYear(), dt.getMonth(), dt.getDate());

    console.log((utc2 - utc1) / _MS_PER_DAY);
    return Math.floor((utc2 - utc1) / _MS_PER_DAY);
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  //  start
  open(content: any) {
    this.modalService
      .open(content, { ariaLabelledBy: 'modal-basic-title' })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
  }
  // open end

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  onSubmit() {
    var a = this.newDemand.value;

    console.log(a);
    return this.dataStorageService.storeDemand(a).subscribe({
      next: (result: any) => {
        console.log(result);
      },
      error: (err: any) => {
        console.log(err);
      },
      complete: () => {
        console.log('complete');
        this.newDemand.reset();
      },
    });
  }
  // Excel Upload start
  onSubmitExcel(_f: NgForm) {
    this.modalService.dismissAll(); //dismiss the modal
  }

  file: any;
  selectFile(event: any) {
    console.log(event);
    this.file = event.target.files[0];
    console.log(this.file);
  }

  uploadFile() {
    return this.dataStorageService.uploadDemand(this.file).subscribe({
      next: (result: any) => {
        console.log(result);
      },
      error: (err: any) => {
        //this.notificationService.setErrorMsg(err.error)
        console.log(err);
      },
      complete: () => {
        console.log('Demand Excel Upload complete');
      },
    });
  }

  //Excel Upload End

  //Detail Start
  openDetails(targetModal: any, demand: Demand) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'md',
    });
    let editedDemandValues = {
      id: demand.id,
      manager: demand.manager,
      created: demand.created,
      // endDate:demand.endDate,
      ageing: demand.ageing,
      priority: demand.priority,
      skill: demand.skill,
      status: demand.status,
    };
    this.detailForm.patchValue(editedDemandValues);
  }
  //Detail End

  //Edit start
  openEdit(targetModal: any, demand: Demand) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'md',
    });
    this.editForm.get('created')?.valueChanges.subscribe((value) => {
      console.log(value);
      var a: any = new Date(value);
      var today = new Date();
      var year = today.toLocaleString('default', { year: 'numeric' });
      var month = today.toLocaleString('default', { month: '2-digit' });
      var day = today.toLocaleString('default', { day: '2-digit' });

      const formattedDate: any = year + '-' + month + '-' + day;
      const dt = new Date(formattedDate);

      const _MS_PER_DAY = 1000 * 60 * 60 * 24;
      const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
      const utc2 = Date.UTC(dt.getFullYear(), dt.getMonth(), dt.getDate());
      console.log(Math.floor((utc2 - utc1) / _MS_PER_DAY).toString());
      this.editForm.patchValue({
        ageing: Math.floor((utc2 - utc1) / _MS_PER_DAY).toString(),
      });

      return value;
    });
    let editedDemandValues = {
      id: demand.id,
      manager: demand.manager,
      created: demand.created,
      ageing: demand.ageing,
      priority: demand.priority,
      skill: demand.skill,
      status: demand.status,
    };
    this.editForm.patchValue(editedDemandValues);

    console.log(this.editForm);
  }
  // Edit End .

  //Save Edit start
  onSave() {
    return this.dataStorageService
      .editDemand(this.editForm.value.id, this.editForm.value)
      .subscribe({
        next: (result: any) => {
          console.log(result);
        },
        error: (err: any) => {
          console.log(err);
        },
        complete: () => {
          console.log('complete');
        },
      });
  }
  //Save Edit End

  // openDelete start
  openDelete(targetModal: any, demand: Demand) {
    this.deleteId = demand.id;
    this.modalService.open(targetModal, {
      backdrop: 'static',
      size: 'md',
    });
  }
  //openDelete End

  //On delete start
  onDelete() {
    return this.dataStorageService.deleteDemand(this.deleteId).subscribe({
      next: (_result: any) => {
        this.modalService.dismissAll();
      },
      error: (_err: any) => {
        this.modalService.dismissAll();
      },
      complete: () => {
        console.log('complete');
      },
    });
  }

  assign(IdData: assign) {
    this.router.navigate(['/assign'], { queryParams: { Id: IdData.id } });
  }
}


