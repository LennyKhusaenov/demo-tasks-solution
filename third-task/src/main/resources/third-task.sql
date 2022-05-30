create sequence viewers_stats_id_seq;
create table viewers_stats
(
    id         bigint       not null default nextval('viewers_stats_id_seq'),
    account_id bigint       not null,
    region     varchar(256) not null,
    created    timestamp    not null default current_timestamp,
    primary key (id)
);
create index region_idx on viewers_stats (region);
create index created_idx on viewers_stats (created);

-- query to show 5 regions with biggest number of unique "views"
-- for the last month sorted by these numbers descending
select vs.region, count(region) views_count
from viewers_stats vs
where vs.created > now() - interval '1 month'
  and vs.created < now()
group by vs.region
order by views_count desc
limit 5;