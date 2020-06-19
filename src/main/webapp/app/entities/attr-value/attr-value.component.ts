import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttrValue } from 'app/shared/model/attr-value.model';
import { AttrValueService } from './attr-value.service';
import { AttrValueDeleteDialogComponent } from './attr-value-delete-dialog.component';

@Component({
  selector: 'jhi-attr-value',
  templateUrl: './attr-value.component.html'
})
export class AttrValueComponent implements OnInit, OnDestroy {
  attrValues?: IAttrValue[];
  eventSubscriber?: Subscription;

  constructor(protected attrValueService: AttrValueService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.attrValueService.query().subscribe((res: HttpResponse<IAttrValue[]>) => (this.attrValues = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAttrValues();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttrValue): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAttrValues(): void {
    this.eventSubscriber = this.eventManager.subscribe('attrValueListModification', () => this.loadAll());
  }

  delete(attrValue: IAttrValue): void {
    const modalRef = this.modalService.open(AttrValueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.attrValue = attrValue;
  }
}
