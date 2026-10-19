package com.api.ovgs.entity;

import com.api.ovgs.domain.SellOrderStatus;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name="tb_ordemVenda")
public class SellOrder { // Ordem de venda

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SellOrderStatus sellOrderStatus;

    @Column(nullable = false)
    private LocalDate scheduling;

    // --------------------------------
    @ManyToOne // Muitos para um
    @JoinColumn(name = "cliente_id") // coluna que será usada para realizar a associação entre as tabelas
    private Client client;

    @ManyToOne
    @JoinColumn(name = "type_transport_id")
    private TypeTransport typeTransport;

    public SellOrder() {}

    public SellOrder(Integer id, SellOrderStatus sellOrderStatus, LocalDate scheduling, Client client, TypeTransport typeTransport) {
        this.id = id;
        this.sellOrderStatus = sellOrderStatus;
        this.scheduling = scheduling;
        this.client = client;
        this.typeTransport = typeTransport;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SellOrderStatus getSellOrderStatus() {
        return sellOrderStatus;
    }

    public void setSellOrderStatus(SellOrderStatus sellOrderStatus) {
        this.sellOrderStatus = sellOrderStatus;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TypeTransport getTypeTransport() {
        return typeTransport;
    }

    public void setTypeTransport(TypeTransport typeTransport) {
        this.typeTransport = typeTransport;
    }

    public LocalDate getScheduling() {
        return scheduling;
    }

    public void setScheduling(LocalDate scheduling) {
        this.scheduling = scheduling;
    }
}
