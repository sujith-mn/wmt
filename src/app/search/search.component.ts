import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
// import { Demand } from '../demand/demand.component';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { __values } from 'tslib';
import { DataStorageService } from '../shared/services/data-storage.service';
import { Demand } from '../shared/model/demand';
import { assign } from '../shared/model/assign';

export interface demandData {
  id: string;
  manager: string;
  created: string;
  endDate : string;
  ageing: string;
  priority: string;
  skill: string;
  status: string;
}
@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit{
 Variable : any ;
 demands: demandData[];
 editForm: FormGroup;
 detailForm:FormGroup;
//  private fb: FormBuilder;
 private deleteId: any;
 closeResult: string;
 displayedColumns:string[] = ['id','manager','created','endDate','ageing','priority','skill','status','actions'];

 statusVal: string[] = ['Open', 'complete', 'pending', 'InProgress'];
 skillVal: string[] = ['Java', 'Angular', 'Spring framework', 'React'];
 dataSource: MatTableDataSource<demandData>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
editedDemandValues: any;


  constructor(
    private httpClient: HttpClient,
    private modalService: NgbModal,  //Add parameter of type NgbModal
    private fb: FormBuilder,
    private  dataStorageService:DataStorageService,
    private router: Router
    ) { }

  newDemand: FormGroup = this.fb.group({
    manager: [null, [Validators.required]],
    created:[null,[ Validators.required]],
    endDate:[null, [Validators.required]],
    ageing: [null, [Validators.required]],
    priority: [null, [Validators.required]],
    skill: [null, [Validators.required]],
    status: [null, [Validators.required]],
  });
  ngOnInit():void {


    this.dataStorageService.refreshneeds.subscribe(() => {
      this.getDemands();
    });
    this.getDemands();
    this.detailForm = this.fb.group({
      id: [''],
      manager: [''],
      created: [''],
      endDate:[''],
      ageing: [''],
      priority: [''],
      skill: [''],
      status: [''],
    });
    this.editForm = this.fb.group({
      id: [''],
      manager: [null, Validators.required],
      created:[null, Validators.required],
      endDate:[null, Validators.required],
      ageing: [null, Validators.required],
      priority: [null, Validators.required],
      skill: [null, Validators.required],
      status: [null, Validators.required],
    });

  }


  // return this.datastorageservice.storePaitent(value)

  getDemands() {
    return this.dataStorageService.getAllDemands().subscribe(
      {
        next: (result: any) => {

        this.demands = result;
        this.dataSource=new MatTableDataSource<demandData>(this.demands);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        },
        error: (err: any) => {
        console.log(err);
        },
        complete: () => {
        console.log('complete');
        }
      }
    )
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
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
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
      return this.dataStorageService.storeDemand(a).subscribe(
          { next: (result: any) => {
            console.log(result);
            },
            error: (err: any) => {
            console.log(err);
            },
            complete: () => {
            console.log('complete');
            }
          }

        )

  }
  // Excel Upload start
  onSubmitExcel(f: NgForm) {
    this.modalService.dismissAll(); //dismiss the modal
  }

  file: any;
  selectFile(event: any) {
    console.log(event);
    this.file = event.target.files[0];
    console.log(this.file);
  }

  uploadFile() {
    return this.dataStorageService.uploadDemand(this.file).subscribe(
      {
        next: (result: any) => {
          console.log(result);
        },
        error: (err: any) => {
          //this.notificationService.setErrorMsg(err.error)
          console.log(err);
        },
        complete: () => {
          console.log('Demand Excel Upload complete');
        }
      }
    )
  }

  //Excel Upload End

  //Detail Start
  openDetails(targetModal: any, demand: Demand) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'md'
    });
    let editedDemandValues = {
      id: demand.id,
      manager: demand.manager,
      created: demand.created,
      endDate:demand.endDate,
      ageing: demand.ageing,
      priority: demand.priority,
      skill: demand.skill,
      status: demand.status,
    }
    this.detailForm.patchValue(editedDemandValues);
  }
  //Detail End

  //Edit start
  openEdit(targetModal: any, demand: Demand) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'md'
    });

    let editedDemandValues = {
      id: demand.id,
      manager: demand.manager,
      created: demand.created,
      endDate:demand.endDate,
      ageing: demand.ageing,
      priority: demand.priority,
      skill: demand.skill,
      status: demand.status,
    }

    this.editForm.patchValue(editedDemandValues);

  console.log(this.editForm);
  }
  // Edit End .

  //Save Edit start
  onSave() {

    return this.dataStorageService.editDemand(this.editForm.value.id,this.editForm.value).subscribe(
      { next: (result: any) => {
        console.log(result)
        },
        error: (err: any) => {
        console.log(err);
        },
        complete: () => {
        console.log('complete');
        }
      }

    )


  }
  //Save Edit End

   // openDelete start
   openDelete(targetModal: any, demand: Demand) {

    this.deleteId = demand.id;
    this.modalService.open(targetModal, {
      backdrop: 'static',
      size: 'md'
    });
  }
  //openDelete End

  //On delete start
  onDelete() {


    return this.dataStorageService.deleteDemand(this.deleteId).subscribe(
      { next: (result: any) => {
        this.modalService.dismissAll();
        },
        error: (err: any) => {
        this.modalService.dismissAll();
        },
        complete: () => {
        console.log('complete');
        }

      });
    }

    assign(IdData: assign){

      this.router.navigate(
        ['/assign'],
        { queryParams: { Id: IdData.id} }
      );

    }
}



