import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm } from '@angular/forms';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { __values } from 'tslib';

export class Demand {

  constructor(
    public id: number,
    public manager: string,
    public created: string,
    public endDate : string,
    public ageing: string,
    public priority: string,
    public skill: string,
    public status: string
  ) {

  }
}
@Component({
  selector: 'app-demand',
  templateUrl: './demand.component.html',
  styleUrls: ['./demand.component.css']
})
export class DemandComponent implements OnInit {
 
  demands: Demand[];
  closeResult: string;               //adding attribute 
  editForm: FormGroup;
  private deleteId: number;
  constructor(
    private httpClient: HttpClient,
    private modalService: NgbModal,  //Add parameter of type NgbModal
    private fb: FormBuilder        //Add parameter of type FormBuilder.
  ) {
  }
  ngOnInit(): void {
    this.getDemands();
    this.editForm = this.fb.group({
      id: [''],
      manager: [''],
      created: [''],
      end_date:[''],
      ageing: [''],
      priority: [''],
      skill: [''],
      status: ['']
    });
  }

  getDemands() {
    this.httpClient.get<any>('http://localhost:7000/api/demands/').subscribe(
      response => {
        console.log(response);
        this.demands = response;
      }
    );
  }
  //  open start
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

  onSubmit(f: NgForm) {

    const headerOptions = new HttpHeaders();
    headerOptions.set('Content-Type', 'application/json');
    const url = 'http://localhost:7000/api/demands/';
    this.httpClient.post(url, f.value, { headers: headerOptions })
      .subscribe((result) => {
        console.log(result);
        this.ngOnInit(); //reload the tablex`
      });
    this.modalService.dismissAll(); //dismiss the modal
  }


  //Detail Start
  openDetails(targetModal: any, demand: Demand) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'lg'
    });
    document.getElementById('manager_Detail')?.setAttribute('value', demand.manager);
    document.getElementById('created_Detail')?.setAttribute('value', demand.created);
    document.getElementById('end_date_Detail')?.setAttribute('value', demand.endDate);
    document.getElementById('ageing_Detail')?.setAttribute('value', demand.ageing);
    document.getElementById('priority_Detail')?.setAttribute('value', demand.priority);
    document.getElementById('skill_Detail')?.setAttribute('value', demand.skill);
    document.getElementById('status_Detail')?.setAttribute('value', demand.status);

  }
  // Detail End


  //Edit start
  openEdit(targetModal: any, demand: Demand) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'lg'
    });
    this.editForm.patchValue({
      id: demand.id,
      manager: demand.manager,
      created: demand.created,
      end_date:demand.endDate,
      ageing: demand.ageing,
      priority: demand.priority,
      skill: demand.skill,
      status: demand.status

    });
  }
  // Edit End .

  //Save Edit start
  onSave() {
    const editURL = 'http://localhost:7000/api/demands/' + this.editForm.value.id;
    console.log(this.editForm.value);
    this.httpClient.put(editURL, this.editForm.value)
      .subscribe((results) => {
        this.ngOnInit();
        this.modalService.dismissAll();
      });
  }
  //Save Edit End


  // openDelete start
  openDelete(targetModal: any, demand: Demand) {
    this.deleteId = demand.id;
    this.modalService.open(targetModal, {
      backdrop: 'static',
      size: 'lg'
    });
  }
  //openDelete End

  //On delete start
  onDelete() {
    const deleteURL = 'http://localhost:7000/api/demands/' + this.deleteId;
    this.httpClient.delete(deleteURL)
      .subscribe((results) => {
        this.ngOnInit();
        this.modalService.dismissAll();
      });
  }

  //On delete End
}





