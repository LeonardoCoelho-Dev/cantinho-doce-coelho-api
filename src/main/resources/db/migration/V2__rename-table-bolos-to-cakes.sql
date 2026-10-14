rename table bolos to cakes;

alter table cakes rename column sabor to flavor;
alter table cakes rename column preco to price;

update cakes set flavor = 'CARROT' where flavor = 'CENOURA';
update cakes set flavor = 'POWDERED_MILK' where flavor = 'LEITENINHO';
update cakes set flavor = 'VANILLA' where flavor = 'BAUNILHA';
